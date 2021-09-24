'''
-*- coding utf-8 -*-
@time:2021/9/24 17:06
@Author:Puwq
@FileName:日期判断.py
'''

def is_leap_year(year):#判断是否是闰年
    if year%400==0 or year%4==0 and year%100!=0:
        return True
    else:
        return False
def get_num_of_days_in_month(year,month):#判断输入月份有多少天
    if month in (1,3,5,7,8,10,12):
        day=31
    elif month in (4,6,9,11):
        day=30
    elif is_leap_year(year):
        day=29
    else:
        day=28
    return day
def get_total_num_of_days(year,month):#距离1800年1月1日有多少天
    total_days=0
    for y in range(1800,year):
        if is_leap_year(y):
            total_days+=366
        else:
            total_days+=365
    for m in range(1,month):
        total_days+=get_num_of_days_in_month(year,m)
    return total_days
def get_start_day(year,month):#获得起始星期,0代表星期天
    return (3+get_total_num_of_days(year,month))%7
 
year=int(input('请输入年份：'))
month=int(input('请输入月份：'))
if year<=1800 or month<1 or month>12:
    print('输入有误！')
else:
    print(get_start_day(year,month))
