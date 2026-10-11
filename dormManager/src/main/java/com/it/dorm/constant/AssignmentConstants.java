package com.it.dorm.constant;

/**
 * 作业常量
 */
public class AssignmentConstants {

    /**
     * 提交状态，0 未批改
     */
    public static final Integer UNCORRECTED = 0;

    /**
     * 提交状态，1 已批改
     */
    public static final Integer CORRECTED = 1;

    /**
     * 提交状态，2 作废
     */
    public static final Integer INVALID = 2;


    // 正常
    public static final String NORMAL = "0";
    // 迟到
    public static final String LATE = "1";
    // 早退
    public static final String EARLY_LEAVE = "2";
    // 缺席
    public static final String ABSENT = "3";
    // 请假
    public static final String ON_LEAVE = "4";
}
