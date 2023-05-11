import tkinter as tk
from tkinter import filedialog
import pandas as pd
import datetime
import json

class ExcelToJsonConverter:
    def __init__(self, master):
        self.master = master
        master.title("Excel to JSON Converter")

        # create the GUI elements
        self.label = tk.Label(master, text="Select an Excel file to convert:")
        self.label.pack()

        self.button = tk.Button(master, text="Select File", command=self.select_file)
        self.button.pack()

        self.convert_button = tk.Button(master, text="Convert", state=tk.DISABLED, command=self.convert_to_json)
        self.convert_button.pack()

        self.status_label = tk.Label(master, text="")
        self.status_label.pack()

        self.file_path = None

    def select_file(self):
        # open a file dialog to select the Excel file
        self.file_path = filedialog.askopenfilename(filetypes=[("Excel files", "*.xlsx")])

        # enable the "Convert" button if a file was selected
        if self.file_path:
            self.convert_button.configure(state=tk.NORMAL)

    def convert_to_json(self):
        if self.file_path:
            try:
                # read the excel file
                df = pd.read_excel(self.file_path)
                #Por dios dejad de llamar a las tablas del Json de entrada con caracteres raros como los de abajo que nos teneis locos haciendo regex y mierdas
                #For God sake stop calling the tables from the input Json with weird characters like the following ones we are falling into madness with the regex
                df.columns = df.columns.str.replace(' ', '_') # replace spaces in column names with underscores
                df.columns = df.columns.str.replace(".", "_", regex=False) # replace periods in column names with underscores
                df.columns = df.columns.str.replace("(", "_", regex=False) # replace opening parentheses in column names with underscores
                df.columns = df.columns.str.replace(")", "_", regex=False)
                df.columns = df.columns.str.replace("/", "_", regex=False)
                df.columns = df.columns.str.replace("\/", "_", regex=False)
                df.columns = df.columns.str.replace("ó", "_", regex=False)
                df.columns = df.columns.str.replace("º", "o", regex=False)
                
                # convert the data to JSON format 
                df = df.fillna(value="None") # convert NaT values to None
                json_data = df.to_json(orient='records', indent=4, date_format='iso')

                # write the JSON data to a file
                json_file_path = self.file_path.replace('.xlsx', '.json')
                with open(json_file_path, 'w') as f:
                    f.write(json_data)

                self.status_label.configure(text="Conversion successful!")
            except Exception as e:
                self.status_label.configure(text="Conversion error: " + str(e))
        else:
            self.status_label.configure(text="No file selected.")

root = tk.Tk()
converter = ExcelToJsonConverter(root)
root.mainloop()
