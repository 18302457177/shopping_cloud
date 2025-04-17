package com.goods.service;

import api.common.Result;
import com.goods.domain.Spu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.goods.dto.AddGoodsDto;
import com.goods.vo.GoodsDetailVO;

import java.util.List;

/**
* @author 86183
* @description 针对表【spu】的数据库操作Service
* @createDate 2025-02-27 11:45:30
*/
public interface SpuService extends IService<Spu> {

    /**
     * 查询静态分类
     * @param storeId
     * @return
     */
    Result<List<Spu>> getStaticCategory(Integer storeId,Integer categoryId);

    /**
     * 获取全部商品
     * @param storeId
     * @return
     */
    Result<List<Spu>> getAllGoodsByStoreId(Integer storeId);

    /**
     * 查询商品
     * @return
     */
    Result<Spu> getAllGoods();

    Result addGoods(AddGoodsDto addGoodsDto);

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    Result<GoodsDetailVO> getGoodsDetail(Integer id);

    /**
     * 查询spu
     * @param spuId
     * @return
     */
    Result<Spu> querySpu(Integer spuId);

    /**
     * 分页查询商品
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Result getGoodsPage(Integer storeId, Integer pageNo, Integer pageSize);

    /**
     * 删除spu
     * @param spuId
     * @return
     */
    Result deleteSpu(Integer spuId);
}
