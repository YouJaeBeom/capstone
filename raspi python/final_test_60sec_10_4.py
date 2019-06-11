
# coding: utf-8

# In[ ]:



import time
import pymysql
from datetime import datetime
import numpy
import socket
import time
import datetime
REPEAT = 10
import pymysql
import time
import numpy
import datetime
import sys

def average(list):
	v = 0
	for i in list:
		v = v + i
	return v / len(list)

def main():
    flag="sleepstart"
    conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
    while True:
        ##1분마다 5분꺼의 데이터를 가져온다.
        time.sleep(1) 
        
        
        ##5분전 시간 
       
        
        c=conn.cursor()
        sql='SELECT * FROM Member_health order by Time desc limit 300'
        c.execute(sql)
        conn.commit()
          
        flag_ing=0
        hr_list1=[]
        rr_list1=[]
        sv_list1=[]
        hrv_list1=[]
        for row in c:
            hr=float(row[2].decode())
            rr=float(row[3].decode())
            sv=float(row[4].decode())
            hrv=float(row[5].decode())
            hr_list1.append(hr)

            rr_list1.append(rr)
            sv_list1.append(sv)
            hrv_list1.append(hrv)
          
        
        
        
        if 0 in hr_list1 and flag=="sleepstart":
            print("침대에서 아무도 없음 취침대기상태")
            ## 자기위해 침대에 누운상태인지 확인해본결과 0이들어와 침대에 누운상태가 아니다.
            
        if 0 in hr_list1 and flag=="sleepexit":
            ## 자고일어난후에 0값이 들어간다.##
            ## 그렇다는거는 자고일어난후 침대밖을나가 인식이 안되어 기상후 침대 밖을 나갔다고 판단
            print("침대밖을 나감")
            flag="sleepstart"
            
        if (hr_list1.count(0)<50) and flag=="sleepexit" :
            ## 자고 일어난후에 침대에 있는 상태
            print("자고일어난후 침대에 있는 상태")
                  
        if (hr_list1.count(0)<50) and flag=="sleepstart" :
            ## 자기위해 침대에 누운상태
            
           
            time_list=[]
            cc_rr=[]
            cc_hr=[]
            array_hr=[]
            array_rr=[]
            up_count =0
            down_count=0
            main_count=0
            count=0
            up_count2 =0
            down_count2=0
            main_count2=0
            count2=0
            judg_hr_array=[]
            judg_rr_array=[]
            hr_intend=[]
            rr_intend=[]
            hr_status=[]
            rr_status=[]
            ####################################
            ## 5분동안 값 확인해본결과 다들어왔다##
            ## 사람이 누웠다고 판단을 한다.     ##
            ####################################
            
            ## 사람이 자는지 안자는지 확인을 한다.##
            
            ##
            conn.close()
            while True:
                judg_rr=""
                judg_hr=""
                print("자기위해 누운상태.")
                time.sleep(1)
                conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
                c=conn.cursor()
                sql='SELECT * FROM Member_health order by Time desc limit 300'
                c.execute(sql)
                conn.commit()
                check=[]
                for row in c:
                    hr=float(row[2].decode())
                    check.append(hr)
                
                if check.count(0)==300:
                    print("기상이라고")
                    ################################
                    ## 값이 0만 5분정도 들어온다면##
                    ## 기상이라고 판단한다.#########
                    ################################
                    conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
                    with conn.cursor() as cursor:
                        Time=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                        sql="insert into `Member_sleep_time_60_10` (`ID`,`Time`,`Status`) values (%s,%s,%s)"
                        cursor.execute(sql,('dbwo4011',Time,'sleepstop'))
                    conn.commit()
                    conn.close()
                    flag="sleepexit"
                    break
                    
                    
                    
                    
                
                
                
                
            
            
                
                c=conn.cursor()
                sql='SELECT HR ,TIME,RR FROM Member_health where HR!=0 order by Time desc limit 300 '

                c.execute(sql)
                conn.commit()
                for row in c:
                    HR=float(row[0].decode())
                    RR=float(row[2].decode())
                    cc_hr.append(HR)
                    TIME=(row[1])
                    time_list.append(TIME)
                    cc_rr.append(RR)
                conn.close()
                #print("전체길이 ",len(cc_hr)) 
                array_hr.append(cc_hr[2])
                array_rr.append(cc_rr[2])
                avg2=numpy.mean(array_rr)
                avg=numpy.mean(array_hr)
                


                if cc_hr[60]+10<=cc_hr[0]:
                    print( "1분전 hr",cc_hr[60],"현재 hr",cc_hr[0] )
                    #print("HR 상승")
                    judg_hr_array.append("증가")
            
                if cc_hr[60]-10>=cc_hr[0]:
                    print("1분전 hr",cc_hr[60],"현재 hr",cc_hr[0] )
                    #print("HR 감소")
                    judg_hr_array.append("감소")

                if cc_hr[0]-10<cc_hr[60] and cc_hr[60]<cc_hr[0]+10 :
                    print("1분전 hr",cc_hr[60],"현재 hr",cc_hr[0] )
                    #print("HR 유지")
                    judg_hr_array.append("유지")


                ##########################################################rr
                if cc_rr[60]+4<=cc_rr[0]:
                    print("1분전 rr",cc_rr[60],"현재 rr",cc_rr[0] )
                    #print("RR 상승")
                    judg_rr_array.append("증가")

                if cc_rr[60]-4>=cc_rr[0]:
                    print("1분전 rr",cc_rr[60],"현재 rr",cc_rr[0] )
                    #print("RR 감소")
                    judg_rr_array.append("감소")

                if cc_rr[0]-4<cc_rr[60] and cc_rr[60]<cc_rr[0]+4:
                    print("1분전 rr",cc_rr[60],"현재 rr",cc_rr[0] )
                    #print("RR 유지")
                    judg_rr_array.append("유지")
                ###################################################################rr 
                ###증가추세 :1 감소추세 2 유지추세 3 불가추세 4
                ##렘상태 3 딥상태 1 라이트 2 
                

                
                ######################################################
                ######################################################
                ###### 추세를 7번 돌려서 확인 하도록한다##############
                ######################################################
                ######################################################
                
                
                ######################
                ## HR에대한 추세확인##
                ######################

                
                
                
                #######################################################
                ## 증가 감소 유지 불규칙 추세 judg_hr_array에 넣는다.##
                #######################################################
                if len(judg_hr_array)>=7 :
                    
                    incre_count=judg_hr_array.count("증가")
                    decre_count=judg_hr_array.count("감소")
                    keep_count=judg_hr_array.count("유지")
                    print("HR incre_count: ",incre_count," decre_count: ",decre_count," keep_count: ",keep_count)
                    
                        
                    hr_intend.append(incre_count)
                    hr_intend.append(decre_count)
                    hr_intend.append(keep_count)
                    
                    ###################################################
                    ## 첫번째 큰 인덱스와 두번째로 큰 인덱스 가져온다##
                    ###################################################
                    hr_intend2=list(hr_intend)
                    hr_intend2.sort()
                    hr_intend2.reverse()
                    print("HR hr_intend: ",hr_intend,"->",hr_intend2)
                    first=hr_intend.index(hr_intend2[0])##index임
                    print("HR first_index ",first,"  point:"+str(hr_intend2[0]))
                    second=hr_intend.index(hr_intend2[1])## index임
                    ####################################################
                    
                    ##############################
                    ##첫번쨰로 큰값이 5이상이면##
                    ## 추세가 압도적으로 많아야 판단할 수 있다 판단함
                    ##############################
                    if  hr_intend[first]>=5:
                        if first==0:
                            ##증가 추세##
                            
                            hr_intend2.clear()
                            judg_hr="증가"
                            print("HR 증가추세입니다1111111")
                        if first==1:
                            ##감소 추세##
                            
                            hr_intend2.clear()
                            judg_hr="감소"
                            print("HR 감소추세입니다11111111111111")
                        if first==2:
                            ##유지 추세##
                            
                            hr_intend2.clear()
                            judg_hr="유지"
                            print("HR 유지추세입니다111111111111111")
                            
                    ##############################
                    ##첫번쨰로 큰값이 5 아래이면##
                    ##############################
                    if hr_intend[first]<5:
                        #####################################################
                        ## 첫번쨰 큰값의 갯수와 두번쨰 큰값의 갯수가 같으면##
                        #####################################################
                            judg_hr="불규칙"
                            hr_intend2.clear()
                            print("HR 불규칙추세입니다444444444444444")
                    hr_intend.clear()


                                
                                
                ######################
                ## RR에대한 추세확인##
                ######################

            

                #######################################################
                ## 증가 감소 유지 불규칙 추세 judg_rr에 넣는다.##
                #######################################################
                

                    incre_count1=judg_rr_array.count("증가")
                    decre_count1=judg_rr_array.count("감소")
                    keep_count1=judg_rr_array.count("유지")
                    print("RR incre_count: ",incre_count1," decre_count: ",decre_count1," keep_count: ",keep_count1)
                    
                        
                    rr_intend.append(incre_count1)
                    rr_intend.append(decre_count1)
                    rr_intend.append(keep_count1)
                    
                    ###################################################
                    ## 첫번째 큰 인덱스와 두번째로 큰 인덱스 가져온다##
                    ###################################################
                    rr_intend2=list(rr_intend)
                    rr_intend2.sort()
                    print("RR rr_intend2: ",rr_intend,"->",rr_intend2)
                    rr_intend2.reverse()
                    first1=rr_intend.index(rr_intend2[0])##index임
                    second1=rr_intend.index(rr_intend2[1])## index임
                    ####################################################
                    
                    print("RR first1_index ",first1,"  point:"+str(rr_intend2[0]))
                    ##############################
                    ##첫번쨰로 큰값이 5 이상이면##
                    ##############################
                    if  rr_intend[first1]>=5:
                        if first1==0:
                            ##증가 추세##
                            
                            rr_intend2.clear()
                            judg_rr="증가"
                            print("RR 증가추세입니다1111111111")
                        if first1==1:
                            ##감소 추세##
                            
                            rr_intend2.clear()
                            judg_rr="감소"
                            print("RR 감소추세입니다11111111111")
                        if first1==2:
                            ##유지 추세##
                           
                            rr_intend2.clear()
                            judg_rr="유지"
                            print("RR 유지추세입니다111111111111111111")
                            
                    ##############################
                    ##첫번쨰로 큰값이 5 아래이면##
                    ##############################
                    if rr_intend[first1]<5:
                        #####################################################
                        ## 첫번쨰 큰값의 갯수와 두번쨰 큰값의 갯수가 같으면##
                        #####################################################
                            judg_rr="불규칙"
                            rr_intend2.clear()
                            print("RR 불규칙추세입니다44444444444")
                    rr_intend.clear()        
                    print("")    
                    
                    
                    ############################################################
                    ############################################################
                    ## judg_rr 과 judg_hr을 비교하여 수면상태 파악하기##########
                    ############################################################
                    ############################################################
                    if judg_rr=="불규칙" and judg_hr=="증가":
                        #################
                        ##  REM SLEEP  ##
                        #################
                        print("REM SLEEP 단계입니다.")
                        judg_rr=""
                        judg_hr=""
                        array_hr.clear()
                        array_rr.clear()
                        ## 다시 초기화 해주고
                        Time=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                        conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
                        with conn.cursor() as cursor:
                           sql="insert into `Member_sleep_step_60_10` (`ID`,`Time`,`Sleep_step`) values (%s,%s,%s)"
                           cursor.execute(sql,('dbwo4011',Time,'3'))
                        conn.commit()
                        conn.close()
                        if flag_ing==0:
                            print("수면 시작")
                            #################
                            ## 첫 수면 시작##
                            ################
                            Time=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                            conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
                            with conn.cursor() as cursor:
                                sql="insert into `Member_sleep_time_60_10` (`ID`,`Time`,`Status`) values (%s,%s,%s)"
                                cursor.execute(sql,('dbwo4011',Time,'sleepstart'))
                            conn.commit()
                            conn.close()
                            
                            flag_ing=1
                        

                        
                        
                        
                    if judg_rr=="감소" and judg_hr=="감소":
                        ##################
                        ## LIGHT SLEEP ##
                        #################
                        print("LIGHT SLEEP 단계입니다.")
                        judg_rr=""
                        judg_hr=""
                        array_hr.clear()
                        array_rr.clear()
                        ## 다시 초기화 해주고
                        Time=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                        conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
                        with conn.cursor() as cursor:
                           sql="insert into `Member_sleep_step_60_10` (`ID`,`Time`,`Sleep_step`) values (%s,%s,%s)"
                           cursor.execute(sql,('dbwo4011',Time,'2'))
                        conn.commit()
                        conn.close()
                        
                        if flag_ing==0:
                            print("수면 시작")
                            #################
                            ## 첫 수면 시작##
                            ################
                            Time=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                            conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
                            with conn.cursor() as cursor:
                                sql="insert into `Member_sleep_time_60_10` (`ID`,`Time`,`Status`) values (%s,%s,%s)"
                                cursor.execute(sql,('dbwo4011',Time,'sleepstart'))
                            conn.commit()
                            conn.close()
                            flag_ing=1
                    if judg_rr=="감소" and judg_hr=="유지":
                        ####################
                        ## DEEP SLEEP 상태##
                        ####################
                        print("DEEP SLEEP 단계입니다.")
                        judg_rr=""
                        judg_hr=""
                        array_hr.clear()
                        array_rr.clear()
                        ## 다시 초기화 해주고
                        Time=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                        conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
                        with conn.cursor() as cursor:
                           sql="insert into `Member_sleep_step_60_10` (`ID`,`Time`,`Sleep_step`) values (%s,%s,%s)"
                           cursor.execute(sql,('dbwo4011',Time,'1'))
                        conn.commit()
                        conn.close()
                    judg_rr=""
                    judg_hr=""
                    judg_rr_array.clear()
                    judg_hr_array.clear()
                    
                    
                    
                
                
                
                                
                            
                            
                            
                        
                        
                
                           
                cc_rr.clear()
                cc_hr.clear()
                time_list.clear()
                count=count+1
                count2=count2+1
                
                    
            
            
            
        

                
            
            
            
            
            
    
    
        
        
        
        
   
    
    

if __name__ == '__main__':
    main()

