package com.msf.fuxi.xdiamond.persistence;

import com.msf.fuxi.xdiamond.domain.Dependency;
import com.msf.fuxi.xdiamond.domain.DependencyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DependencyMapper {

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  int countByExample(DependencyExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  int deleteByExample(DependencyExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  int deleteByPrimaryKey(Integer id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  int insert(Dependency record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  int insertSelective(Dependency record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  List<Dependency> selectByExample(DependencyExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  Dependency selectByPrimaryKey(Integer id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  int updateByExampleSelective(@Param("record") Dependency record,
                               @Param("example") DependencyExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  int updateByExample(@Param("record") Dependency record,
                      @Param("example") DependencyExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  int updateByPrimaryKeySelective(Dependency record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dependency
   * @mbggenerated
   */
  int updateByPrimaryKey(Dependency record);
}