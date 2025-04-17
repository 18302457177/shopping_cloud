package com.goods.service.impl;

import api.common.MinioHost;
import api.common.PageResult;
import api.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.goods.config.RedisCache;
import com.goods.domain.Sku;
import com.goods.domain.SkuParam;
import com.goods.domain.Spu;
import com.goods.dto.AddGoodsDto;
import com.goods.dto.SkuDto;
import com.goods.mapper.SkuMapper;
import com.goods.mapper.SkuParamMapper;
import com.goods.service.SkuService;
import com.goods.service.SpuService;
import com.goods.mapper.SpuMapper;
import com.goods.vo.GoodsDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86183
* @description 针对表【spu】的数据库操作Service实现
* @createDate 2025-02-27 11:45:30
*/
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu>
    implements SpuService{

    @Autowired
    SpuMapper spuMapper;

    @Autowired
    RedisCache redisCache;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SkuParamMapper skuParamMapper;

    /**
     * 查询静态分类
     *
     * @param storeId
     * @return
     */
    @Override
    public Result<List<Spu>> getStaticCategory(Integer storeId,Integer categoryId) {

        List<Spu> spus = spuMapper.selectList(new LambdaQueryWrapper<Spu>()
                .eq(Spu::getStoreId, storeId)
                .eq(Spu::getCategory3Id,categoryId));
        if (spus.isEmpty()){
            return Result.fail("没有数据");
        }

        return Result.success(spus);
    }

    /**
     * 获取全部商品
     *
     * @param storeId
     * @return
     */
    @Override
    public Result<List<Spu>> getAllGoodsByStoreId(Integer storeId) {
        List<Spu> spus = spuMapper.selectList(new LambdaQueryWrapper<Spu>().eq(Spu::getStoreId, storeId));
        if (spus.isEmpty()){
            return Result.fail("没有数据");
        }
        return Result.success(spus);
    }

    /**
     * 查询商品
     *
     * @return
     */
    @Override
    public Result<Spu> getAllGoods() {
        //List<Spu> goodsData = redisCache.getCacheList("goodsData");
        //if (!goodsData.isEmpty()){
        //    return Result.success(goodsData);
        //}
        List<Spu> spus = spuMapper.selectList(new LambdaQueryWrapper<Spu>().eq(Spu::getSaleStatus, 1));
        redisCache.setCacheList("goodsData",spus);
        return Result.success(spus);
    }

    @Override
    public Result addGoods(AddGoodsDto addGoodsDto) {
        Spu spu = new Spu();
        BeanUtils.copyProperties(addGoodsDto.getGoodsData(),spu);
        spu.setCategory3Id(addGoodsDto.getCategory3_id());
        if (!addGoodsDto.getImgMd5().isEmpty()){
            String filePathByMd5 = getFilePathByMd5(addGoodsDto.getImgMd5(), addGoodsDto.getImgSuffix());
            spu.setImg(MinioHost.MINIO_HOST+MinioHost.FILE+filePathByMd5);
        }

        if (!addGoodsDto.getVideoMd5().isEmpty()){
            String videoPathByMd5 = getVideoPathByMd5(addGoodsDto.getVideoMd5(), addGoodsDto.getVideoSuffix());
            spu.setVideo(MinioHost.MINIO_HOST+MinioHost.VIDEO+videoPathByMd5);
        }
        int insert = spuMapper.insert(spu);
        if (insert<0){
            return Result.fail("插入数据失败");
        }
        Spu spu1 = spuMapper.selectOne(new LambdaQueryWrapper<Spu>().eq(Spu::getStoreId,addGoodsDto.getGoodsData().getStoreId())
                .eq(Spu::getName,addGoodsDto.getGoodsData().getName()));
        for (int i = 0; i < addGoodsDto.getSkuList().size(); i++) {
            Sku sku = new Sku();
            sku.setSpuId(spu1.getId());
            sku.setStoreId(spu.getStoreId());
            sku.setCategory3Id(addGoodsDto.getCategory3_id());
            sku.setParam(addGoodsDto.getSkuList().get(i).getSkuName());
            int insert1 = skuMapper.insert(sku);
            if (insert1>0){
                int size = addGoodsDto.getSkuList().get(i).getParam().size();
                for (int j = 0; j < size; j++) {
                    SkuParam skuParam = new SkuParam();
                    skuParam.setId(0);
                    skuParam.setSkuId(sku.getId());
                    skuParam.setParam(addGoodsDto.getSkuList().get(i).getParam().get(j));
                    skuParam.setStoreId(spu.getStoreId());
                    skuParamMapper.insert(skuParam);
                }
            }
        }
        return Result.success();
    }

    private String getFilePathByMd5(String fileMd5,String fileExt){
        return   fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5.substring(2,3) + "/"  + fileMd5+"." +fileExt;
    }
    private String getVideoPathByMd5(String fileMd5,String fileExt){
        return   fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5.substring(2,3) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }

    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
    @Override
    public Result<GoodsDetailVO> getGoodsDetail(Integer id) {

        Spu spu = spuMapper.selectById(id);
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        BeanUtils.copyProperties(spu,goodsDetailVO);
        List<Sku> skus = skuMapper.selectList(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getSpuId, spu.getId())
                .eq(Sku::getStoreId, spu.getStoreId()));
        ArrayList<SkuDto> skuDtos = new ArrayList<>();
        for (Sku sku:skus){
            SkuDto skuDto = new SkuDto();
            BeanUtils.copyProperties(sku,skuDto);
            List<SkuParam> skuParams = skuParamMapper.selectList(new LambdaQueryWrapper<SkuParam>()
                    .eq(SkuParam::getSkuId, sku.getId())
                    .eq(SkuParam::getStoreId, sku.getStoreId()));
            skuDto.setChildren(skuParams);
            skuDtos.add(skuDto);
        }
        goodsDetailVO.setSkuDtoList(skuDtos);

        return Result.success(goodsDetailVO);
    }


    /**
     * 查询spu
     *
     * @param spuId
     * @return
     */
    @Override
    public Result<Spu> querySpu(Integer spuId) {
        Spu spu = spuMapper.selectById(spuId);
        return Result.success(spu);
    }

    /**
     * 分页查询商品
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Result getGoodsPage(Integer storeId, Integer pageNo, Integer pageSize) {
        Integer count = spuMapper.selectCount(new LambdaQueryWrapper<Spu>().eq(Spu::getStoreId, storeId));
        PageHelper.startPage(pageNo,pageSize);
        List<Spu> spus = spuMapper.selectList(new LambdaQueryWrapper<Spu>().eq(Spu::getStoreId, storeId));
        PageResult<Spu> result = new PageResult<>();
        result.setTotal(count);
        result.setData(spus);
        return Result.success(result);
    }

    /**
     * 删除spu
     *
     * @param spuId
     * @return
     */
    @Transactional
    @Override
    public Result deleteSpu(Integer spuId) {
        Spu spu = spuMapper.selectById(spuId);
        List<Sku> skus = skuMapper.selectList(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getStoreId, spu.getStoreId())
                .eq(Sku::getSpuId, spuId));

        ArrayList<Integer> skuList = new ArrayList<>();
        for (Sku sku:skus){
            skuList.add(sku.getId());
        }
        spuMapper.deleteById(spuId);
        skuMapper.deleteBatchIds(skuList);
        for (int i = 0; i < skuList.size(); i++) {
            List<SkuParam> skuParams = skuParamMapper.selectList(new LambdaQueryWrapper<SkuParam>().eq(SkuParam::getSkuId, skuList.get(i)));
            ArrayList<Integer> skuParamsList = new ArrayList<>();
            for (SkuParam skuParam:skuParams){
                skuParamsList.add(skuParam.getId());
            }
            skuParamMapper.deleteBatchIds(skuParamsList);
        }
        return Result.success();
    }
}




