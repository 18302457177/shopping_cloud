<template>
    <div class="middle">
        <div class="left">
            <img src="@\sources\pictures\logo-201305-b.png" alt="">
            <span>我的京松</span>
        </div>
    </div>
</template>

<script setup lang="ts" name="Logo">

</script>

<style scoped lang="scss">
.middle {
    width: 100%;
    height: 80px;

    .left {
        width: 40%;
        display: flex;
        align-items: center;
        justify-content: center;

        span {
            margin-left: 30px;
            margin-top: 17px;
            font-size: 30px;
            color: #dcd1d1;
            font-weight: 700;
        }
    }
}
</style>