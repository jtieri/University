import tkinter
from tkinter import *
from tkinter import filedialog, messagebox
import sklearn
from sklearn import tree
import random
from random import seed, randint
import os
import numpy
import pandas as pd
import matplotlib.pyplot as plt

from ML_Script import *


root = Tk()
root.title('Gini Coefficient Helper')
root.geometry("825x600")


#seperating window into sections 
frameTop = Frame(root)
frameTop.pack()
frameMiddle = Frame(root)
frameMiddle.pack()
frameBottom = Frame(root)
frameBottom.pack()


#looks for file and returns pathname and then returns the file read in a global variable so everyone in the UI can access this CSV data and will not have to load twice.
def openFile():
    filepath = filedialog.askopenfilename(filetypes=(("csv files", "*.csv"), ("all files", "*.*")))
    entryFilePath.delete(0, END)
    entryFilePath.insert(0, filepath)
    if not(filepath.endswith(".csv")):
        m = messagebox.showinfo(message="Error, the Selected file is not a CSV file")
        entryStatus.delete(0, END)
        entryStatus.insert(0, "Invalid File - Not a (csv)data file.")
    else:
        entryStatus.delete(0, END)
        entryStatus.insert(0, "Valid Data File - Ready for Prediction") #updates status window
        global fileRead
        fileRead = pd.read_csv(filepath)
        
#revised function: took out the logic where gets the filepath from the askopenFilename and am now just using this to output some variables
#also write to the UI
def runButton():
        global y_test, y_pred, classifier
        dataInfo, confMatrix, class_report, acc, accP, bal_acc, y_test, y_pred, classifier = dataLoading(fileRead)
        
        dataInfo = dataInfo.split("\n")
        class_report = class_report.split("\n")

        lstDataInfo.delete(0, END)
        index = 0
        for x in dataInfo:
            lstDataInfo.insert(index, x)
            index = index + 1

        lstAccuracy.delete(0, END)
        lstAccuracy.insert(0, "Balanced Accuracy: " + str(bal_acc))
        lstAccuracy.insert(0, "Accuracy: " + str(accP))
        lstAccuracy.insert(0, "Accuracy: " + str(acc))
        index = 0
        for x in class_report:
            lstAccuracy.insert(index, x)
            index = index + 1
        
#new addition: makes attribute vs gini scatter plot
def attributeVsGiniGraph(event):
    dataset = fileRead
    x_axis_Data = dataset['GINIALLRF']
    y_axis_Data = dataset.drop('GINIALLRF',axis=1)
    for key, value in drpDwnOptions.items():
        if clicked.get() == key:
            plt.figure(figsize=(10,10))
            plt.scatter(x_axis_Data, y_axis_Data[value], c="red")
            plt.plot([x_axis_Data.min(),x_axis_Data.max()], [y_axis_Data[value].min(),y_axis_Data[value].max()], lw=5)
            plt.title(key + ' vs Gini Coefficient')
            plt.xlabel("Gini Coefficient")
            plt.ylabel(key)
            plt.show()

#revised function: Once data is loaded it will allow user to push button to display graph.
def displayAccuracyGraph():
    fig, ax = plt.subplots()
    ax.scatter(y_test, y_pred, edgecolors=(0, 0, 0))
    ax.plot([y_test.min(), y_test.max()], [y_test.min(), y_test.max()], lw=3)
    ax.set_xlabel('Encoded actual values')
    ax.set_ylabel('Encoded predicted values')
    ax.set_title("Gini Coefficient Prediction Accuracy")
    plt.show()

#revised function: once data is loaded it will allow user to push button to display tree
def displayTree():
    okaokayfig = plt.figure(figsize=(20, 10))
    _ = tree.plot_tree(classifier, filled=True)
    plt.show()

def fileSave():
    filepath = filedialog.askdirectory()
    entrySaveLocation.insert(0, filepath)

def reset():
    entryFilePath.delete(0, END)
    entrySaveLocation.delete(0, END)
    entryStatus.delete(0, END)
    lstAccuracy.delete(0, END)
    lstDataInfo.delete(0, END)

def save():
    filepath = entrySaveLocation.get()
    if entryFilePath.get() == "":
        m = messagebox.showinfo(message="Please Select a csv file.")
    elif filepath == "":
        m = messagebox.showinfo(message="Please Select a destination to Save the results.")
    else:
        _dir = os.path.join(filepath + '/', 'output/')
        seed(randint(5, 50))
        randInt = randint(0, 25)

        if not os.path.exists(_dir):
            os.makedirs(_dir)

        text = lstDataInfo.get(0, END)

        try:
            with open(_dir + 'Data_Information_' + str(randInt) + '.txt', mode='r+',
                      encoding='UTF-8', errors='strict', buffering=1) as fh:
                fh.truncate(0)
                for x in text:
                    fh.write(x)
                    fh.write('\n')
        except FileNotFoundError:
            with open(_dir + 'Data_Information_' + str(randInt) + '.txt', mode='w+',
                      encoding='UTF-8', errors='strict', buffering=1) as fh:
                for x in text:
                    fh.write(x)
                    fh.write('\n')
        fh.close()

        text = lstAccuracy.get(0, END)

        try:
            with open(_dir + 'Accuracy_Report_' + str(randInt) + '.txt', mode='r+',
                      encoding='UTF-8', errors='strict', buffering=1) as fh:
                fh.truncate(0)
                for x in text:
                    fh.write(x)
                    fh.write('\n')
        except FileNotFoundError:
            with open(_dir + 'Accuracy_Report_' + str(randInt) + '.txt', mode='w+',
                      encoding='UTF-8', errors='strict', buffering=1) as fh:
                for x in text:
                    fh.write(x)
                    fh.write('\n')
        fh.close()


#drop down menu options as a dictionary to be able to use keys as user facing value    
drpDwnOptions = {
"Average Hourly Earnings of Production and Nonsupervisory Employees,Total Private":"AHETPI",
"Employment Population Ratio":"EMRATION",
"Population":"POPTHM",
"Hours of Wage and Salary Workers on Non-farm Payrolls: Total":"TOTLQ",
"Federal Government total expenditures":"W019RCQ027SBEA",
"Gross National Income":"A023RC1Q027SBEA",
"All Sectors: Compensation of Employees Paid":"ASCOEPQ027S",                
"All Sectors: Current Taxes on Income, Wealth,etc. Paid":"ASTIWEQ027S",
"Gross Domestic Product":"GDP",
"Gross National Product":"GNP",
"Federal government current tax receipts":"W006RC1Q027SBEA",
"Unemployment Rate":"UNRATE",
"Government":"Government",
"Household and Nonprofit Organizations; ":"HNICTIQ027S",
"National Income: Corporate profits before tax":"National Income",
"Gross Federal Debt":"FYGFD",
"U.S Individual Income Tax":"IITTRHB",
"Gross Domestic Income":"GDI"
}

#------------------ Beginning top frame ---------------------


lblSelectFile = Label(frameTop, text="Select a csv file:")
lblSelectFile.grid(row=0, column=0, sticky=E)


entryFilePath = Entry(frameTop, width=70)
entryFilePath.grid(row=0, column=1, pady=5)


btnBrowse = Button(frameTop, text="Browse", command=openFile)
btnBrowse.grid(row=0, column=2,pady=5,sticky=W)


lblStatus = Label(frameTop, text="Status:")
lblStatus.grid(row=1, column=0, sticky=E)


entryStatus = Entry(frameTop, width=70)
entryStatus.grid(row=1, column=1, padx=5, pady=5)

btnRun = Button(frameTop, text="Run", command=runButton)
btnRun.grid(row=1, column=2,pady=5,sticky=W)

btnAccuracyGraph = Button(frameTop, text="Display Accuracy Graph", command=displayAccuracyGraph)
btnAccuracyGraph.grid(row=2, column=0)

clicked = StringVar()
clicked.set("Attribute vs Gini Coefficient Graph")
dropDwnMenu = OptionMenu(frameTop, clicked, *drpDwnOptions, command=attributeVsGiniGraph)
dropDwnMenu.grid(row=2, column=1)

btnDisplayTree = Button(frameTop, text="Display Tree", command=displayTree)
btnDisplayTree.grid(row=2, column=2, sticky=W)


#------------------ Beginning middle frame ---------------------

lblPrediction = Label(frameMiddle, text="Data Information:")
lblPrediction.grid(row=3, column=0, padx=5, pady=15)

lblAccuracy = Label(frameMiddle, text="Accuracy:")
lblAccuracy.grid(row=3, column=1, padx=5, pady=15) 

lstDataInfo = Listbox(frameMiddle, width=50, height=20)
lstDataInfo.grid(row=4, column=0, padx=5, pady=5)

lstAccuracy = Listbox(frameMiddle, width=50, height=20)
lstAccuracy.grid(row=4, column=1, padx=5, pady=5)



#------------------ Beginning bottom frame ---------------------

lblSaveLocation = Label(frameBottom, text="Choose Where to Save the Results:")
lblSaveLocation.grid(row=5, column=0,pady=1)

entrySaveLocation = Entry(frameBottom, width=70)
entrySaveLocation.grid(row=6, column=0, padx=15)

btnSaveBrowse = Button(frameBottom, text="Browse", command=fileSave)
btnSaveBrowse.grid(row=6, column=1,pady=1, sticky=E)


btnSave = Button(frameBottom, text="Save", command=save)
btnSave.grid(row= 7, column=0, padx = 5,pady=5,sticky=E)
btnReset = Button(frameBottom, text="Reset", command=reset)
btnReset.grid(row=7, column=1, padx=5, pady=5,sticky=W)



root.mainloop()
