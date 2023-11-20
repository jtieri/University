#Written by Myriam Changoluisa 
#Term: Spring 2020

#Importing Packages
import pandas as pd
import numpy as np
import scipy.stats as sp
import matplotlib.pyplot as plt
import time
import datetime
from sklearn import linear_model

#Importing data
#Enddate : 2020 - 07 - 01
AHETPI = pd.read_excel (r'./data_folder/AHETPI_quaterly.xlsx')
AHETPI.set_index('Date', inplace=True)

EMRATIO = pd.read_excel (r'./data_folder/EMRATIO_quaterly.xlsx', skiprows = range(1,65) )
EMRATIO.set_index('Date', inplace=True)

POPTHM = pd.read_excel (r'./data_folder/POPTHM_quaterly.xls', skiprows = range(1,21))
POPTHM.set_index('Date', inplace=True)

UNRATE = pd.read_excel (r'./data_folder/UNRATE_quaterly.xlsx', skiprows = range(1,65))
UNRATE.set_index('date', inplace=True)

#------------
TOTLQ = pd.read_excel ((r'./data_folder/TOTLQ.xls'))
TOTLQ.set_index('observation_date', inplace=True)

W019RCQ027SBEA = pd.read_excel (r'./data_folder/W019RCQ027SBEA.xls', skiprows = range(1,20))
W019RCQ027SBEA.set_index('observation_date', inplace=True)

#-------------

#Enddate : 2020 - 04 - 01
Government= pd.read_excel (r'./data_folder/Goernment_Tax_receipts.xls', skiprows = range(1,25))
Government.set_index('observation_date', inplace=True)

HNICTIQ027S= pd.read_excel (r'./data_folder/HNICTIQ027S.xls', skiprows = range(1,70))
HNICTIQ027S.set_index('observation_date', inplace=True)

NationalIncome = pd.read_excel (r'./data_folder/National Income.xls', skiprows = range(1,69))
NationalIncome.set_index('observation_date', inplace=True)

GDI = pd.read_excel (r'./data_folder/GDI.xlsx')
GDI.set_index('DATE', inplace=True)

#-----------
A023RC1Q027SBEA = pd.read_excel (r'./data_folder/A023RC1Q027SBEA.xls', skiprows = range(1,69))
A023RC1Q027SBEA.set_index('observation_date', inplace=True)

ASCOEPQ027S = pd.read_excel (r'./data_folder/ASCOEPQ027S.xls', skiprows = range(1,70))
ASCOEPQ027S.set_index('observation_date', inplace=True)

ASTIWEQ027S = pd.read_excel (r'./data_folder/ASTIWEQ027S.xls', skiprows = range(1,21))
ASTIWEQ027S.set_index('observation_date', inplace=True)

GDP = pd.read_excel (r'./data_folder/GDP.xls', skiprows = range(1,69))
GDP.set_index('observation_date', inplace=True)

GNP = pd.read_excel (r'./data_folder/GNP.xls', skiprows = range(1,69))
GNP.set_index('observation_date', inplace=True)

W006RC1Q027SBEA = pd.read_excel (r'./data_folder/W006RC1Q027SBEA.xls', skiprows = range(1,69))
W006RC1Q027SBEA.set_index('observation_date', inplace=True)
#-----------

#Enddate : 2019 - 10 - 01
FYGFD = pd.read_excel (r'./data_folder/FYGFD_quaterly.xlsx', skiprows = range(1,65) )
FYGFD.set_index('Date', inplace=True)

GINIALLRF= pd.read_excel (r'./data_folder/GINIALLRF_quaterly.xls', skiprows = range(1,65))
GINIALLRF.set_index('Date', inplace=True)

#Enddate : 2016 - 10 - 01 
IITTRHB = pd.read_excel (r'./data_folder/IITTRHB_quaterly.xls', skiprows = range(1,65))
IITTRHB.set_index('Date', inplace=True)

#Enddate : 2020 - 04 - 01 / start 2002 can not be predicted
NPPTTL = pd.read_excel (r'./data_folder/NPPTTL_Quaterly.xlsx')
NPPTTL.set_index('Date', inplace=True)

#Linear Regression
def linearFunctionToPredictValues(nameString):
    y=np.array(nameString.values, dtype=float)
    x=np.array(pd.to_datetime(nameString.dropna()).index.values, dtype=float)
    slope, intercept, r_value, p_value, std_err =sp.linregress(x,y)
    xf = np.linspace(min(x),max(x),100)
    xf1 = xf.copy()
    xf1 = pd.to_datetime(xf1)
    yf = (slope*xf)+intercept
    #print(' r = ', r_value, '\n', 'p = ', p_value, '\n', 's = ', std_err, '\n','slope = ', slope, '\n', 'intercept  = ', intercept )
    #print(' slope = ', slope, '\n', 'intercept  = ', intercept )
    return slope, intercept
    
def createNewDates(inital, final, slope1, intercept1):
    index = pd.date_range(inital, final, 2)
    index, len(index)
    new_arr = np.array(index.values, dtype = float)
    yf_new_values = (slope1*new_arr)+intercept1
    print('The new predicted values are:' , yf_new_values)
   
def createNewDates_16(inital, final, slope1, intercept1):
    index = pd.date_range(inital, final, 16)
    index, len(index)
    new_arr = np.array(index.values, dtype = float)
    yf_new_values = (slope1*new_arr)+intercept1
    print('The new predicted values are:' , yf_new_values)

def createNewDates_4(inital, final, slope1, intercept1):
    index = pd.date_range(inital, final, 4)
    index, len(index)
    new_arr = np.array(index.values, dtype = float)
    yf_new_values = (slope1*new_arr)+intercept1
    print('The new predicted values are:' , yf_new_values)

#Prediction values
slope , intercept = linearFunctionToPredictValues(AHETPI['AHETPI'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(EMRATIO['EMRATIO'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(POPTHM['POPTHM'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(UNRATE['UNRATE'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(TOTLQ['TOTLQ'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(W019RCQ027SBEA['W019RCQ027SBEA'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(A023RC1Q027SBEA['A023RC1Q027SBEA'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(ASCOEPQ027S['ASCOEPQ027S'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(ASCOEPQ027S['ASCOEPQ027S'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(ASTIWEQ027S['ASTIWEQ027S'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(GDP['GDP'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(GNP['GNP'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(W006RC1Q027SBEA['W006RC1Q027SBEA'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(Government['B245RC1Q027SBEA'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(HNICTIQ027S['HNICTIQ027S'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(NationalIncome['A053RC1Q027SBEA'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(GDI['GDI'])

start1 = datetime.datetime(2020, 7, 1)
end1 = datetime.datetime(2020, 10, 1)

createNewDates(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(FYGFD['FYGFD'])

start = datetime.datetime(2020, 1, 1)
end = datetime.datetime(2020, 10, 1)

createNewDates_4(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(GINIALLRF['GINIALLRF'])

start = datetime.datetime(2020, 1, 1)
end = datetime.datetime(2020, 10, 1)

createNewDates_4(start1, end1, slope, intercept )

slope , intercept = linearFunctionToPredictValues(IITTRHB['IITTRHB'])

start = datetime.datetime(2017, 1, 1)
end = datetime.datetime(2020, 10, 1)

createNewDates_16(start1, end1, slope, intercept )

#Concatenating DataFrames
# Place the DataFrames side by side
horizontal_stack = pd.concat([AHETPI, EMRATIO, POPTHM, TOTLQ, W019RCQ027SBEA, A023RC1Q027SBEA, ASCOEPQ027S, ASTIWEQ027S, GDP, GNP, W006RC1Q027SBEA, UNRATE, Government, HNICTIQ027S, NationalIncome, FYGFD, IITTRHB, GDI,GINIALLRF ], axis=1)
horizontal_stack

newdata = horizontal_stack.dropna()
newdata

newdata.to_csv('preprocessdata_2.csv', index = True)