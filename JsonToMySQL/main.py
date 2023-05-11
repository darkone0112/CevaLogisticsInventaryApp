import tkinter as tk
from tkinter import filedialog
import json
import mysql.connector
import os
class JsonToMySQLConverter:
    def __init__(self, master):
        self.master = master
        master.title("JSON to MySQL Converter")

        # create the GUI elements
        self.label1 = tk.Label(master, text="Select a JSON file to convert:")
        self.label1.pack()

        self.json_button = tk.Button(master, text="Select File", command=self.select_json_file)
        self.json_button.pack()

        self.label2 = tk.Label(master, text="Select a MySQL database to insert the data:")
        self.label2.pack()

        self.db_button = tk.Button(master, text="Select Database", command=self.select_database)
        self.db_button.pack()

        self.convert_button = tk.Button(master, text="Convert", state=tk.DISABLED, command=self.convert_to_mysql)
        self.convert_button.pack()

        self.status_label = tk.Label(master, text="")
        self.status_label.pack()

        self.json_file_path = None
        self.database = None

    def select_json_file(self):
        # open a file dialog to select the JSON file
        self.json_file_path = filedialog.askopenfilename(filetypes=[("JSON files", "*.json")])

        # enable the "Convert" button if a file was selected
        if self.json_file_path and self.database:
            self.convert_button.configure(state=tk.NORMAL)

    def select_database(self):
        # open a dialog to select the MySQL database
        self.database = "inventary"

        # enable the "Convert" button if a file was selected
        if self.json_file_path and self.database:
            self.convert_button.configure(state=tk.NORMAL)

    def convert_to_mysql(self):
        if self.json_file_path and self.database:
            try:
                # extract the table name from the JSON file name
                table_name = os.path.splitext(os.path.basename(self.json_file_path))[0]

                # connect to the MySQL database
                cnx = mysql.connector.connect(
                    host="localhost",
                    user="VsCode",
                    password="2458",
                    database=self.database
                )

                # create a cursor object
                cursor = cnx.cursor()

                # read the JSON file
                with open(self.json_file_path) as f:
                    json_data = json.load(f)

                # create the table if it doesn't already exist
                keys = json_data[0].keys()
                query = "CREATE TABLE IF NOT EXISTS {} ({})".format(
                    table_name,
                    ", ".join(["{} VARCHAR(255)".format(key) for key in keys])
                )
                print(query)
                cursor.execute(query)

                # loop through the records and insert them into the database
                for record in json_data:
                    values = [record[key] for key in keys]

                    # format the query string
                    query = "INSERT INTO {} ({}) VALUES ({})".format(
                        table_name,
                        ", ".join(keys),
                        ", ".join(["%s" for _ in range(len(keys))])
                    )

                    # execute the query
                    cursor.execute(query, values)

                # commit the changes
                cnx.commit()

                # close the cursor and the connection
                cursor.close()
                cnx.close()

                self.status_label.configure(text="Conversion successful!")
            except mysql.connector.Error as err:
                self.status_label.configure(text="MySQL Error: {}".format(err.msg))
        else:
            self.status_label.configure(text="No file or database selected.")



root = tk.Tk()
converter = JsonToMySQLConverter(root)
root.mainloop()
