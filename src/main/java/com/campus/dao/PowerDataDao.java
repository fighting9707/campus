package com.campus.dao;

import java.util.LinkedList;
import java.util.List;

import com.campus.pojo.*;
import org.apache.ibatis.annotations.Param;


public interface PowerDataDao {
    /**
     * 添加购电记录
     *
     * @param list 购电记录列表
     * @return 添加结果
     */
    public int insertPowerBuyData(@Param("list") LinkedList<PowerBuyData> list);

    /**
     * 添加用电记录
     *
     * @param list 用电记录列表
     * @return 添加结果
     */
    public int insertPowerUseData(@Param("list") LinkedList<PowerUseData> list);

    /**
     * 查询出最新一笔购电记录
     *
     * @param powerInfo 宿舍信息,需要包括宿舍楼和宿舍号
     * @return 返回购电记录
     */
    public PowerBuyData selectFirstPowerBuy(PowerInfo powerInfo);

    /**
     * 查询出最新一笔用电记录
     *
     * @param powerInfo 宿舍信息,需要包括宿舍楼和宿舍号
     * @return 返回用电记录
     */
    public PowerUseData selectFirstPowerUse(PowerInfo powerInfo);

    /**
     * 查询用电记录
     *
     * @param powerInfo 宿舍信息,需要包括宿舍楼和宿舍号
     * @return 返回用电记录
     */
    public List<PowerUseData> selectPowerUseData(PowerInfo powerInfo);

    /**
     * 查询购电记录
     *
     * @param powerInfo 宿舍信息,需要包括宿舍楼和宿舍号
     * @return 返回购电记录
     */
    public List<PowerBuyData> selectPowerBuyData(PowerInfo powerInfo);



    public int insertSouthPowerBuyData(@Param("list") LinkedList<SouthPowerUseData> list);


    public int insertSouthPowerUseData(@Param("list") LinkedList<SouthPowerBuyData> list);


    public List<SouthPowerUseData> selectSouthPowerUseData(SouthPowerInfo southPowerInfo);
    public List<SouthPowerBuyData> selectSouthPowerBuyData(SouthPowerInfo southPowerInfo);
    public SouthPowerBuyData selectFirstSouthPowerBuy(SouthPowerInfo southPowerInfo);
    public SouthPowerUseData selectFirstSouthPowerUse(SouthPowerInfo southPowerInfo);
}
