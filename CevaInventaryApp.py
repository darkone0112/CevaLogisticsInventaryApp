from tkinter import simpledialog
import tkinter as tk
from tkinter import messagebox
from tkinter import filedialog
import mysql.connector
import csv
import json
import os
from mysql.connector import Error
#todo:
#Reminder: There is a lot of redundant code in the row functions, must be optimized
            
#The list of operation are working great, but need to constraint the abailavility of the operations to the site, for example Barajas site has no Amazone operation
# --->DONE Add new function to move data from the inventory table and stock table to the obsolete table
# --->DONE Must remade the DDBB structure with the new design
        #The new design will have a table for each kind of device inventory for example
            #ComputersInventory
            #DisplaysInventory
        #And a table for each kind of device stock for example
            #ComputersStock
            #DisplaysStock
        #Add a Table for obsolete devices
            #ComputersObsolete
            #DisplaysObsolete
        
# --->DONE The Update and Add function must have some sort of fixed option for the critical columns
                # Model
                # OPS
                # SITE
        # The update part must be tested more in depth
        
# --->PENDING Add some kind of relation between BU and the computer/user/operation(Still need the data)
    #Maybe we can use mackup data to test the viability of the Function in the actual DB design
    
# --->DONE(must change the prefix) Add Stock tables (may be for each kind of device?)
        #New design will have only a Computer table and various stock tables for each kind of device
        #So a ComputersInventory and a ComputersStock and for example a DisplaysInventory and a DisplaysStock
        #Instead of the actual approach of having a table for each site
        #More Details in top comment
    
# --->DONE Function to add deleted data to the Stock table

# Function to add new OC to the Stock table as pending

# -->DONE Function to export data from a given table to a .csv file
    # Also will be interesting to export data from an specific filter query result


from tkinter import *
from tkinter import messagebox, ttk

class DatabaseApp:
    columns_with_manual_filtering = ["Name_Device", "S_N", "User", "User_AD", "PO_Number", "id"]
    def __init__(self, root):
        self.root = root
        self.root.state('zoomed')
        self.root.configure(bg='#002147') # Set background color of root
        #Other Options:
        #---> #4d4dff Neon Blue
        #---> #4B0082 Soft Purple
        #----> #002147 Dark Blue
        #Slightly Lighter: #003366 - This is a slightly lighter shade of the dark blue, which could be used for background elements to give a bit of contrast.

            #Medium Light: #004080 - This is a medium-light shade of blue, which could be used for buttons or other interactive elements.

            #Even Lighter: #0055a4 - This is a much lighter shade of blue, which could be used for highlighting or selecting.

            #Light Blue: #0066cc - This is a light blue shade, which could be used for active elements or notifications.

            #Very Light Blue: #0073e6 - This is a very light shade of blue, almost sky blue. It could be used for highlighting important information or buttons.
        self.last_filter = None
        self.root.title("Ceva Inventary App")
        self.root.geometry("1920x1080")
        self.column_configurations = {}
        # Connection
        self.connection = self.create_connection("localhost", "VsCode", "2458", "inventary")
        
        # Create cursor
        self.context_menu = Menu(self.root, tearoff=0)
        self.context_menu.add_command(label='Update', command=self.update_row_dialog)
        self.context_menu.add_command(label='Assign', command=self.main_row)
        self.context_menu.add_command(label='Stock', command=self.stock_row)
        self.context_menu.add_command(label='Obsolete', command=self.obsolete_row)
        self.context_menu.add_command(label='Delete', command=self.delete_row)
        
        # Create UI elements
        self.menu_bar = Menu(self.root, bg='grey', fg='white')  # Set menu colors
        self.root.config(menu=self.menu_bar)
        
        self.table_menu = Menu(self.menu_bar, bg='#003366', fg='white')  # Set table menu colors
        self.menu_bar.add_cascade(label='Tables', menu=self.table_menu)
        self.table_menu.add_command(label='Computers', command=lambda: self.change_table('computers'))
        self.table_menu.add_command(label='StockComputers', command=lambda: self.change_table('stockComputers'))
        self.table_menu.add_command(label='ObsoleteComputers', command=lambda: self.change_table('obsoleteComputers'))
        self.table_menu.add_command(label='Monitors', command=lambda: self.change_table('beta_inventory_barajas'))
        self.table_menu.add_command(label='ricoh', command=lambda: self.change_table('ricoh'))
        self.table_menu.add_command(label='zebra', command=lambda: self.change_table('zebraont'))

                # Add button
        self.actions_menu = Menu(self.menu_bar, bg='#003366', fg='white')  # Set actions menu colors
        self.menu_bar.add_cascade(label='Actions', menu=self.actions_menu)
        # Add new row
        self.actions_menu.add_command(label='Add New', command=self.add_new_row)
        self.actions_menu.add_command(label='Add New PO', command=self.insert_to_stock_computers)
        self.actions_menu.add_command(label='Filter', command=self.filter_dialog)
        self.actions_menu.add_command(label='Export', command=self.export_to_csv)
        self.actions_menu.add_command(label='Refresh', command=self.refresh_table)
        
        self.databases_menu = Menu(self.menu_bar, bg='#003366', fg='white')  # Set actions menu colors
        self.menu_bar.add_cascade(label='DataBases', menu=self.databases_menu)
        self.databases_menu.add_command(label='Update List', command=self.add_data)
        

        # Add status label
        #self.status_label = tkinter.Label(root, text="")
        #self.status_label.pack()

        # Initialize currently selected table and row
        self.current_table = None
        self.current_row = None

        # Create Treeview
        style = ttk.Style()
        style.configure("Treeview", background="#002147", foreground="white", fieldbackground="#002147")
        style.configure("Treeview.Heading", background="#002147", foreground="black")  # Set treeview heading colors
        self.tree = ttk.Treeview(self.root, style="Treeview", height=700)
        self.tree.pack()

        # Bind row select event
        self.tree.bind('<<TreeviewSelect>>', self.on_row_selected)
        
        
        #Display default table
        self.change_table('computers')
        
        # Hide the first column
        self.tree.column('#0', width=0, stretch=NO)
        
        #Bind right click event
        self.tree.bind("<Button-3>", self.show_context_menu)
    def create_connection(self, host_name, user_name, user_password, db_name):
        connection = None
        try:
            connection = mysql.connector.connect(
                host=host_name,
                user=user_name,
                passwd=user_password,
                database=db_name
            )
            print("Connection to MySQL DB successful")
        except Error as e:
            print(f"The error '{e}' occurred")

        return connection

    def change_table(self, table_name):
        self.current_table = table_name

        # Fetch data from table
        cursor = self.connection.cursor()
        cursor.execute(f"SELECT * FROM {table_name}")

        # Get column names
        column_names = [i[0] for i in cursor.description]
        
        # Clear treeview
        for i in self.tree.get_children():
            self.tree.delete(i)

        # Configure treeview columns
        self.tree["columns"] = column_names
        if table_name in self.column_configurations:
            # If a configuration exists for this table, apply it
            for col, width in self.column_configurations[table_name].items():
                self.tree.column(col, width=220, anchor="center")
        else:
            # Otherwise, create a new configuration
            self.column_configurations[table_name] = {}
            for col in column_names:
                self.tree.column(col, width=220, anchor="center")  # adjust the width to suit your needs
                self.column_configurations[table_name][col] = 220  # Store the width

        # Set column titles for each column
        for col in column_names:
            self.tree.heading(col, text=col)

        # Insert new data
        rows = cursor.fetchall()
        for row in rows:
            self.tree.insert('', 'end', values=row)
            
        # Hide the first column 
        self.tree.column('id', width=0, stretch=NO)
        
        # Set the height of the treeview based on the number of rows
        self.tree.configure(height=len(rows))




    def on_row_selected(self, event):
        selected_items = self.tree.selection()  # get selected items
        if selected_items:  # check if there are any selected items
            selected_item = selected_items[0]
            self.current_row = self.tree.item(selected_item)['values']



    def delete_row(self):
        if self.current_row:
            cursor = self.connection.cursor()
            try:
                # Check if the current table is a stock table
                if self.current_table.startswith("obsolete") or self.current_table.startswith("stock"):
                    confirmation = messagebox.askquestion("Delete", "Are you sure you want to delete this data?")
                    if confirmation == 'yes':
                        # Execute DELETE statement without inserting into another table
                        cursor.execute(f"DELETE FROM {self.current_table} WHERE id = {self.current_row[0]}")
                        self.connection.commit()
                        print("Row deleted successfully from stock table")
                        self.change_table(self.current_table)
                else:
                    self.error_box("Delete Error", "Cannot Delete Directly From The Inventory Table")
                    #self.status_label.configure(text="Cannot Delete Directly From The Inventory Table")
                    print("Cannot Delete Directly From The Inventory Table")
            except Exception as e:
                print(f"Error occurred: {e}")
        else:
            print("No row selected")
            
    def stock_row(self):
        if self.current_row:
            cursor = self.connection.cursor()
            try:
                if self.current_table == "stockComputers" or self.current_table == "obsoleteComputers" or self.current_table == "computers":
                    stock_table = "stockComputers"
                    obsolete_table = "obsoleteComputers"
                    main_table = "computers"
                elif self.current_table == "stockMonitors" or self.current_table == "obsoleteMonitors" or self.current_table == "monitors":
                    stock_table = "stockMonitors"
                    obsolete_table = "obsoleteMonitors"
                    main_table = "monitors"
                if self.current_table == "stockComputers":
                    self.message_box("Error", "Cannot Stock From Stock Table")
                    print("Row already in stock table")
                else:
                    cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
                    column_names = [column[0] for column in cursor.fetchall()]

                    insert_columns = ", ".join(column_names)
                    insert_values = f"SELECT {', '.join(column_names)} FROM {self.current_table} WHERE id = {self.current_row[0]}"
                    insert_query = f"INSERT INTO {stock_table} ({insert_columns}) {insert_values}"

                    cursor.execute(insert_query)
                    self.connection.commit()

                    cursor.execute(f"DELETE FROM {self.current_table} WHERE id = {self.current_row[0]}")
                    self.connection.commit()

                    self.change_table(self.current_table)
                    print("Row deleted successfully and inserted into stock table")
            except Exception as e:
                print(f"Error occurred: {e}")
        else:
            print("No row selected")

    def obsolete_row(self):
        if self.current_row:
            cursor = self.connection.cursor()
            try:
                if self.current_table == "stockComputers" or self.current_table == "obsoleteComputers" or self.current_table == "computers":
                    stock_table = "stockComputers"
                    obsolete_table = "obsoleteComputers"
                    main_table = "computers"
                elif self.current_table == "stockMonitors" or self.current_table == "obsoleteMonitors" or self.current_table == "monitors":
                    stock_table = "stockMonitors"
                    obsolete_table = "obsoleteMonitors"
                    main_table = "monitors"
                if self.current_table == "obsoleteComputers":
                    self.message_box("Error", "Row already in obsolete table")
                    print("Row already in obsolete table")
                else:
                    cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
                    column_names = [column[0] for column in cursor.fetchall()]

                    insert_columns = ", ".join(column_names)
                    insert_values = f"SELECT {', '.join(column_names)} FROM {self.current_table} WHERE id = {self.current_row[0]}"
                    insert_query = f"INSERT INTO {obsolete_table} ({insert_columns}) {insert_values}"

                    cursor.execute(insert_query)
                    self.connection.commit()

                    cursor.execute(f"DELETE FROM {self.current_table} WHERE id = {self.current_row[0]}")
                    self.connection.commit()

                    self.change_table(self.current_table)
                    print("Row deleted successfully and inserted into obsolete table")
            except Exception as e:
                print(f"Error occurred: {e}")
        else:
            print("No row selected")
            
            
    def main_row(self):
        if self.current_row:
            cursor = self.connection.cursor()
            try:
                if self.current_table == "stockComputers" or self.current_table == "obsoleteComputers" or self.current_table == "computers":
                    main_table = "computers"
                elif self.current_table == "stockMonitors" or self.current_table == "obsoleteMonitors" or self.current_table == "monitors":
                    main_table = "monitors"
                if self.current_table == "computers" or self.current_table == "monitors":
                    self.message_box("Cannot Assign", "Data already in computers table")
                    print("Data already in main table")
                else:
                    confirmation = messagebox.askokcancel("Assign", "Remember to update before assing to the main table", icon='info')
                    if confirmation == True:
                        cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
                        column_names = [column[0] for column in cursor.fetchall()]

                        insert_columns = ", ".join(column_names)
                        insert_values = f"SELECT {', '.join(column_names)} FROM {self.current_table} WHERE id = {self.current_row[0]}"
                        insert_query = f"INSERT INTO {main_table} ({insert_columns}) {insert_values}"

                        cursor.execute(insert_query)
                        self.connection.commit()

                        cursor.execute(f"DELETE FROM {self.current_table} WHERE id = {self.current_row[0]}")
                        self.connection.commit()

                        self.change_table(self.current_table)
                        print("Row deleted successfully and inserted into obsolete table")
            except Exception as e:
                print(f"Error occurred: {e}")
                self.message_box("Error", "Error occurred: {e}")
        else:
            print("No row selected")
            self.message_box("Error", "No row selected", icon='error')





    def update_row(self, new_values):
        if self.current_row:
            cursor = self.connection.cursor()
            
            # Fetch column names
            cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
            column_names = [column[0] for column in cursor.fetchall()]

            update_query = f"UPDATE {self.current_table} SET "

            # Join all new_values with the corresponding column names
            for i in range(len(column_names)):
                if i < len(new_values):
                    update_query += f"{column_names[i]} = '{new_values[i]}', "

            # Remove the trailing comma and space
            update_query = update_query.rstrip(', ')

            update_query += f" WHERE {column_names[0]} = {self.current_row[0]}"

            cursor.execute(update_query)
            self.connection.commit()
            self.change_table(self.current_table)  # Refresh table



    def filter_table(self, column_name, value):
        cursor = self.connection.cursor()
        cursor.execute(f"SELECT * FROM {self.current_table} WHERE {column_name} LIKE '%{value}%'")
        rows = cursor.fetchall()

        # Get column names
        column_names = [i[0] for i in cursor.description]

        # Clear treeview
        for i in self.tree.get_children():
            self.tree.delete(i)

        # Configure treeview columns
        self.tree["columns"] = column_names
        if self.current_table in self.column_configurations:
            # If a configuration exists for this table, apply it
            for col, width in self.column_configurations[self.current_table].items():
                self.tree.column(col, width=120, anchor="center")
        else:
            # Otherwise, create a new configuration
            self.column_configurations[self.current_table] = {}
            for col in column_names:
                self.tree.column(col, width=120, anchor="center")  # adjust the width to suit your needs
                self.column_configurations[self.current_table][col] = 120  # Store the width

        # Set column titles for each column
        for col in column_names:
            self.tree.heading(col, text=col)

        # Insert new data
        for row in rows:
            self.tree.insert('', 'end', values=row)

        # Adjust the height of the treeview
        self.tree.configure(height=min(len(rows), 700))  # adjust the maximum height to your preference
        self.last_filter = (column_name, value)



    def show_context_menu(self, event):
    # Select row under mouse
        self.tree.identify_row(event.y)
        self.context_menu.post(event.x_root, event.y_root)

    def update_row_dialog(self):
        # Check if a row has been selected
        if not self.current_row:
            messagebox.showinfo("No row selected", "Please select a row to update")
            return

        # Create a new Toplevel window
        update_dialog = Toplevel(self.root)
        update_dialog.title("Update row")

        # Create an Entry for each field in the row
        entries = []
        cursor = self.connection.cursor()
        cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
        column_names = [column[0] for column in cursor.fetchall()]

        # Generate widgets for each row
        self.generate_row_widgets(update_dialog, column_names, entries)

        # Set the value of each widget to the current value of the row
        for entry, value in zip(entries, self.current_row[1:]):  # starting from index 1 to skip 'id' column
            if isinstance(entry, StringVar):
                entry.set(value)
            else:
                entry.insert(0, value)

        # Add a button that updates the row when clicked
        Button(update_dialog, text="Update", command=lambda: self.update_row([self.current_row[0]] + [e.get() if isinstance(e, Entry) else e.get() for e in entries])).grid(row=len(column_names), column=0, columnspan=2, pady=10)

    
    def generate_row_widgets(self, add_dialog, column_names, entries):
        def create_option_menu(add_dialog, options, i):
            option_var = StringVar()
            option_var.set(options[0])
            option_menu = OptionMenu(add_dialog, option_var, *options)
            option_menu.grid(row=i, column=1)
            return option_var

        # Load the data from JSON
        if os.path.exists('Json/dictionaries.json'):
            with open('Json/dictionaries.json') as f:
                option_dict = json.load(f)
        else:
            with open('../Json/dictionaries.json', 'w') as f:
                json.dump({}, f)

        for i, column_name in enumerate(column_names[1:], start=1):  # starting from 1 to skip 'id' column
            Label(add_dialog, text=f"{column_name}").grid(row=i, column=0)
            if column_name.upper() in option_dict:
                option_var = create_option_menu(add_dialog, option_dict[column_name.upper()], i)
                entries.append(option_var)
            else:
                entry = Entry(add_dialog)
                entry.grid(row=i, column=1)
                entries.append(entry)


    def add_new_row(self):
        # Create a new Toplevel window
        add_dialog = Toplevel(self.root)
        add_dialog.title("Add new row")

        # Create an Entry for each field in the row
        entries = []
        if self.current_table == "computers" or self.current_table == "monitors" or self.current_table == "ricoh" or self.current_table == "zebra":
            cursor = self.connection.cursor()
            cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
            column_names = [column[0] for column in cursor.fetchall()]

            # Generate widgets for each row
            self.generate_row_widgets(add_dialog, column_names, entries)
            # Add a button that adds the row when clicked
            Button(add_dialog, text="Add", command=lambda: self.insert_row([e.get() for e in entries])).grid(row=len(column_names), column=0, columnspan=2)
        else:
            self.error_box("Error", "Cannot assign to: " + self.current_table + " in only possible to assign to one of the main tables")
    def get_next_id(self):
        cursor = self.connection.cursor()

        tables = ["computers", "stockcomputers", "obsoletecomputers"]
        max_id = 0

        for table in tables:
            cursor.execute(f"SELECT MAX(id) FROM {table}")
            result = cursor.fetchone()[0]
            if result is not None and result > max_id:
                max_id = result

        return max_id + 1

    
    #Here will be the code for add new Computer as stock to the Computers Stock Table
    #Using the OC and the rest of the values as pending
    #def insert_oc(self, new_values):
    def insert_to_stock_computers(self):
        # Create a new Toplevel window
        add_dialog = Toplevel(self.root)
        add_dialog.title("Add new row to stock computers")

        # Create an Entry for each field in the row
        entries = []
        cursor = self.connection.cursor()
        cursor.execute(f"SHOW COLUMNS FROM stockcomputers")
        column_names = [column[0] for column in cursor.fetchall()]

        # Generate widgets for each row
        self.generate_row_widgets(add_dialog, column_names, entries)
        
        # Add a button that adds the row when clicked
        Button(add_dialog, text="Add", command=lambda: self.add_and_close_dialog(add_dialog, [e.get() for e in entries])).grid(row=len(column_names), column=0, columnspan=2)
    
    def insert_row_stock_computers(self, values):
        try:
            cursor = self.connection.cursor()

            # Get the number of columns in the table
            cursor.execute(f"SHOW COLUMNS FROM stockcomputers")
            column_names = [column[0] for column in cursor.fetchall()]

            next_id = self.get_next_id()  # Get the next valid id

            values = [next_id] + values  # Prepend the id to the list of values

            # Check if the number of columns matches the number of values provided
            if len(column_names) != len(values):
                messagebox.showerror("Error", "The number of values provided does not match the number of columns in the table")
                return False

            placeholders = ", ".join(["%s"] * len(values))
            query = f"INSERT INTO stockcomputers ({', '.join(column_names)}) VALUES ({placeholders})"

            cursor.execute(query, values)
            self.connection.commit()

            messagebox.showinfo("Success", "Row inserted successfully")
            return True  # Return True on success
            
        except Exception as e:
            messagebox.showerror("Error", f"An error occurred: {str(e)}")
            return False  # Return False on failure
        
    def add_and_close_dialog(self, add_dialog, values):
        if self.insert_row_stock_computers(values):
            add_dialog.destroy()  # Close the add_dialog window if data insertion is successful
                
    def insert_row(self, new_values):
        cursor = self.connection.cursor()
        
        # Fetch column names
        cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
        column_names = [column[0] for column in cursor.fetchall()][1:]  # Skip 'id' column

        insert_query = f"INSERT INTO {self.current_table} ({', '.join(column_names)}) VALUES ({', '.join(['%s'] * len(new_values))})"
        cursor.execute(insert_query, new_values)
        self.connection.commit()
        self.change_table(self.current_table)  # Refresh table
        
    def filter_dialog(self):
        # Create a new Toplevel window
        filter_dialog = Toplevel(self.root)
        filter_dialog.title("Filter")

        # List to hold all filters
        filters = []

        def add_filter():
            # Fetch column names from the database
            cursor = self.connection.cursor()
            cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
            column_names = [column[0] for column in cursor.fetchall()]


            # Create StringVar objects to hold the selected column and filter value
            column_name_var = StringVar(filter_dialog)
            column_name_var.set(column_names[0])
            filter_value_var = StringVar(filter_dialog)

            # Create a frame to hold the filter value widget
            filter_value_frame = Frame(filter_dialog)

            # Function to update the filter value widget
            def update_value_widget(*args):
                # Clear the frame
                for widget in filter_value_frame.winfo_children():
                    widget.destroy()

                # If the column is in columns_with_manual_filtering, create an Entry
                if column_name_var.get() in self.columns_with_manual_filtering:
                    filter_value_entry = Entry(filter_value_frame, textvariable=filter_value_var)
                    filter_value_entry.pack()
                else:  # Otherwise, create an OptionMenu
                    cursor.execute(f"SELECT DISTINCT {column_name_var.get()} FROM {self.current_table}")
                    unique_values = [row[0] for row in cursor.fetchall()]
                    filter_value_menu = OptionMenu(filter_value_frame, filter_value_var, *unique_values)
                    filter_value_menu.pack()

            # Update the filter value widget whenever the column selection changes
            column_name_var.trace('w', update_value_widget)

            # Store the filter in the list of filters
            filters.append([column_name_var, filter_value_var])

            # Display the filter
            OptionMenu(filter_dialog, column_name_var, *column_names).grid(row=len(filters), column=0)
            filter_value_frame.grid(row=len(filters), column=1)

            # Initial update of the filter value widget
            update_value_widget()

        def apply_filters():
            for filter in filters:
                column_name = filter[0].get()
                filter_value = filter[1].get()
                self.filter_table(column_name, filter_value)

        # Add filter button
        Button(filter_dialog, text="Add filter", command=add_filter).grid(row=100, column=0, columnspan=2)

        # Apply filters button
        Button(filter_dialog, text="Apply filters", command=apply_filters).grid(row=101, column=0, columnspan=2)
        
    def add_data(self):
        with open(os.path.join('Json', 'dictionaries.json'), 'r') as f:
            option_dict = json.load(f)

        def save_data():
            key = key_var.get()
            value = value_entry.get()

            if not key or not value:
                messagebox.showerror("Error", "Both fields must be filled out")
                return

            option_dict[key].append(value)

            with open(os.path.join('Json', 'dictionaries.json'), 'w') as f:
                json.dump(option_dict, f, indent=3)

            messagebox.showinfo("Success", "Data added successfully")

        def delete_data():
            key = key_var.get()
            value = value_entry.get()

            if not key or not value:
                messagebox.showerror("Error", "Both fields must be filled out")
                return

            if value in option_dict[key]:
                option_dict[key].remove(value)

                with open(os.path.join('Json', 'dictionaries.json'), 'w') as f:
                    json.dump(option_dict, f, indent=4)

                messagebox.showinfo("Success", "Data deleted successfully")
            else:
                messagebox.showerror("Error", "Value not found in key")

        add_dialog = Toplevel()
        add_dialog.title("Add/Delete Data")

        Label(add_dialog, text="Key:").grid(row=0, column=0)

        # Create a StringVar to hold the selected option
        key_var = StringVar()
        if option_dict:
            # Set it to the first key of the dictionary by default
            key_var.set(list(option_dict.keys())[0])

        key_option_menu = OptionMenu(add_dialog, key_var, *option_dict.keys())
        key_option_menu.grid(row=0, column=1)

        Label(add_dialog, text="Value:").grid(row=1, column=0)
        value_entry = Entry(add_dialog)
        value_entry.grid(row=1, column=1)

        Button(add_dialog, text="Save", command=save_data).grid(row=2, column=0)
        Button(add_dialog, text="Delete", command=delete_data).grid(row=2, column=1)


    def export_to_csv(self):
        cursor = self.connection.cursor()

        # Define the query to get all the data
        query = f"SELECT * FROM {self.current_table}"
        if self.last_filter:
            query += f" WHERE {self.last_filter[0]} LIKE '%{self.last_filter[1]}%'"

        # Execute the query
        cursor.execute(query)

        # Fetch all the data
        data = cursor.fetchall()

        # Define column names
        cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
        column_names = [column[0] for column in cursor.fetchall()]

        # Open a file dialog to choose where to save the CSV file
        csv_file_name = filedialog.asksaveasfilename(defaultextension='.csv', filetypes=[('CSV Files', '*.csv')])

        # Check if a file name was chosen
        if not csv_file_name:
            print("No file name chosen for CSV export.")
            return

        # Write data to the chosen CSV file
        try:
            with open(csv_file_name, 'w', newline='') as csvfile:
                writer = csv.writer(csvfile)
                writer.writerow(column_names)  # header
                writer.writerows(data)
            print(f"Data exported successfully to {csv_file_name}")
        except Exception as e:
            print(f"Error occurred: {e}")

        
    def refresh_table(self):
        self.change_table(self.current_table)
    
    def message_box(self, title, message):
        # Show the message box
        messagebox.showinfo(title, message, parent=self.root)
        
    def confirm_box(self, title, message):
        # Show the confirmation box
        return messagebox.askyesno(title, message, parent=self.root)
    
    def error_box(self, title, message):
        # Show the error box
        messagebox.showerror(title, message, parent=self.root)




root = Tk()
app = DatabaseApp(root)
root.mainloop()

