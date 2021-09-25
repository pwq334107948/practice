'''
-*- coding utf-8 -*-
@time:2021/9/24 17:06
@Author:Puwq
@FileName:日期判断.py
'''

def leap_year(year):#判断闰年
    if year%400==0 or year%4==0 and year%100!=0:
        return True
    else:
        return False
def days_in_month(year,month):#每个月有多少天
    if month in (1,3,5,7,8,10,12):
        return 31
    elif month in (4,6,9,11):
        return 30
    elif leap_year(year):
        return 29
    else:
        return 28
def total_days(year,month):#距离给定的条件1900年1月1日有多少天
    total_days=0
    for y in range(1900,year):
        if leap_year(y):
            total_days+=366
        else:
            total_days+=365
    for m in range(1,month):
        total_days+=days_in_month(year,m)
    return total_days
def start_day(year,month):#判断输入月份的起始星期
    start_day=(1+total_days(year,month))%7
    if start_day==0:
        return '星期天'
    elif start_day==1:
        return '星期一'
    elif start_day==2:
        return '星期二'
    elif start_day==3:
        return '星期三'
    elif start_day==4:
        return '星期四'
    elif start_day==5:
        return '星期五'
    else:
        return '星期六'

year=int(input('请输入年份：'))
month=int(input('请输入月份：'))
if year<=1800 or month<1 or month>12:
    print('输入有误！')
else:
    print(year,'年',month,'月的第一天是',start_day(year,month))
