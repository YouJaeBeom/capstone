
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
from sdk.api.message import Message
from sdk.exceptions import CoolsmsException

def average(list):
	v = 0
	for i in list:
		v = v + i
	return v / len(list)

def main():
    flag="sleepstart"
    while True:
        ##1분마다 5분꺼의 데이터를 가져온다.
        time.sleep(1) 
        
        api_key = "NCS1U0DWBO1YTJJ7"
        api_secret = "NNOWCDCZNQ9RBUPPOYNUX8ASWTLTAST1"
        ##5분전 시간 
        params = dict()
        params['type'] = 'sms' # Message type ( sms, lms, mms, ata )
        params['to'] = '01097173995' # Recipients Number '01000000000,01000000001'
        params['from'] = '01062042561' # Sender number
        params['text'] = '스트레스 지수가 높습니다!' # Message
        cool = Message(api_key, api_secret)
        
        
        
        conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
        c=conn.cursor()
        sql='SELECT * FROM Member_health order by Time desc limit 300'
        c.execute(sql)
        conn.close()  
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
            
            while True:
                time.sleep(1)
                
                ############################################
                # sdnn구해서 그 최신값시간대에 넣기       #
                #############################################
                nn_interval=[]
                for i in range(300):
                    rr.append(float(60000/hr_list1[i]))
                variance = 0
                k=0
                mean = numpy.average(rr)
                std = numpy.std(rr)
                
                while 1:
                    variance+=(rr[k]-mean)**2
                    k=k+1
                    if len(rr)==k+1 :
                        variance=variance/(len(rr)-1)
                        break
                std = numpy.sqrt(variance)
                Time=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
                conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
                
                with conn.cursor() as cursor:
                    sql="insert into `Member_sdnn` (`ID`,`Start_time`,`stress_value`) values (%s,%s,%s)"
                    cursor.execute(sql,('dbwo4011',Time,str(std)))
                conn.commit()
                conn.close
                
                ###############################################
                #여기까지가 sdnn구하는 식                #
                #############################################
                
                
                ######################################################################
                # sdnn수치가 40이상 :  정상 상태 , 수면 조명등 
                # 40<sndd)30 인경우 : 경계 스트레스 , 긴장감 완화 수면등
                # 30<sdnn    인경우 : 극도의 스트레스 ,위험군 , 문자메시지 전송
                ##########################################################
               # if std >40 :
               #     
               # if std<=40 and std>=30 :
               #     
                if std<30 :
                     try:
                            response = cool.send(params)
                            print("Success Count : %s" % response['success_count'])
                            print("Error Count : %s" % response['error_count'])
                            print("Group ID : %s" % response['group_id'])

                            if "error_list" in response:
                                print("Error List : %s" % response['error_list'])

                    except CoolsmsException as e:
                            print("Error Code : %s" % e.code)
                            print("Error Message : %s" % e.msg)

                    sys.exit()
                 ##################################################################   
                 #   문자 메시지 발송                                           ####
                #####################################################################    
                
                conn = pymysql.connect(host='dbwo4011.cafe24.com', user='dbwo4011', password='ajswl1234', database='dbwo4011')
                c=conn.cursor()
                sql='SELECT * FROM Member_health order by Time desc limit 300'
                c.execute(sql)
                conn.close()
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
                    
                    flag="sleepexit"
                    break
                 
                
                    
            
            
            
        

                
            
            
            
            
            
    
    
        
        
        
        
   
    
    

if __name__ == '__main__':
    main()

