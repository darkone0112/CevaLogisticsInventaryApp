from tkinter import simpledialog
import mysql.connector
from mysql.connector import Error
#todo:
# Must remade the DDBB structure with the new design
    #The new design will have a table for each kind of device inventory for example
        #ComputersInventory
        #DisplaysInventory
    #And a table for each kind of device stock for example
        #ComputersStock
        #DisplaysStock
        
# --->DONE The Update and Add function must have some sort of fixed option for the critical columns
                # Model
                # OPS
                # SITE
        # The update part must be tested more in depth
        
# Add some kind of relation between BU and the computer/user/operation(Still need the data)
    #Maybe we can use mackup data to test the viability of the Function in the actual DB design
    
#--->ALMOST DONE (must change the prefix) Add Stock tables (may be for each kind of device?)
    #New design will have only a Computer table and various stock tables for each kind of device
    #So a ComputersInventory and a ComputersStock and for example a DisplaysInventory and a DisplaysStock
    #Instead of the actual approach of having a table for each site
    #More Details in top comment
    
#--->DONE Function to add deleted data to the Stock table

# Function to add new OC to the Stock table as pending

# Function to export data from a given table to a .csv file
    # Also will be interesting to export data from an specific filter query result


from tkinter import *
from tkinter import messagebox, ttk

class DatabaseApp:
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
        
        self.root.title("Ceva Inventary App")
        self.root.geometry("1920x1080")
        self.column_configurations = {}
        # Connection
        self.connection = self.create_connection("localhost", "VsCode", "2458", "inventary")
        
        # Create cursor
        self.context_menu = Menu(self.root, tearoff=0)
        self.context_menu.add_command(label='Update', command=self.update_row_dialog)
        self.context_menu.add_command(label='Delete', command=self.delete_row)
        
        # Create UI elements
        self.menu_bar = Menu(self.root, bg='grey', fg='white')  # Set menu colors
        self.root.config(menu=self.menu_bar)
        self.table_menu = Menu(self.menu_bar, bg='#003366', fg='white')  # Set table menu colors
        self.menu_bar.add_cascade(label='Tables', menu=self.table_menu)
        
        self.table_menu.add_command(label='barajasComputers', command=lambda: self.change_table('computersBarajas'))
        self.table_menu.add_command(label='barajasStock', command=lambda: self.change_table('stockBarajas'))
        self.table_menu.add_command(label='barajasDisplays', command=lambda: self.change_table('beta_inventory_barajas'))
        self.table_menu.add_command(label='ricoh', command=lambda: self.change_table('ricoh'))
        self.table_menu.add_command(label='zebraont', command=lambda: self.change_table('zebraont'))

                # Add button
        self.actions_menu = Menu(self.menu_bar, bg='#003366', fg='white')  # Set actions menu colors
        self.menu_bar.add_cascade(label='Actions', menu=self.actions_menu)
        
        # Add new row
        self.actions_menu.add_command(label='Add New', command=self.add_new_row)
        self.actions_menu.add_command(label='Filter', command=self.filter_dialog)
        self.actions_menu.add_command(label='Refresh', command=self.refresh_table)



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
        self.change_table('beta_inventory_barajas')
        
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
                if self.current_table.startswith("stock"):
                    # Execute DELETE statement without inserting into another table
                    cursor.execute(f"DELETE FROM {self.current_table} WHERE id = {self.current_row[0]}")
                    self.connection.commit()
                    print("Row deleted successfully from stock table")
                    self.change_table(self.current_table)
                else:
                    # Retrieve column names from the current table
                    cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
                    column_names = [column[0] for column in cursor.fetchall()]

                    # Generate the stock table name based on the current table name
                    table_name_parts = self.current_table.split("computers")  # Assuming the table name format is "computers{suffix}"
                    if len(table_name_parts) == 2:
                        stock_table_name = f"stock{table_name_parts[1]}"
                    else:
                        stock_table_name = f"stock{self.current_table}"

                    # Generate the INSERT statement for the stock table
                    insert_columns = ", ".join(column_names)
                    insert_values = f"SELECT {', '.join(column_names)} FROM {self.current_table} WHERE id = {self.current_row[0]}"
                    insert_query = f"INSERT INTO {stock_table_name} ({insert_columns}) {insert_values}"

                    # Execute INSERT statement
                    cursor.execute(insert_query)
                    self.connection.commit()

                    # Execute DELETE statement
                    cursor.execute(f"DELETE FROM {self.current_table} WHERE id = {self.current_row[0]}")
                    self.connection.commit()

                    self.change_table(self.current_table)  # Refresh table
                    print("Row deleted successfully and inserted into stock table")
            except Exception as e:
                print(f"Error occurred: {e}")
        else:
            print("No row selected")





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

        option_dict = {
            #Computers and Monitors dictionary entries
            'OPS': ["OPS1", "OPS2", "OPS3"],
            'WFH': ["OPS1", "OPS2", "OPS3"],
            'SITE': ["SITE1", "SITE2", "SITE3"],
            'TYPE': ["Desktop", "Laptop"],
            'MODEL': ["Model1", "Model2", "Model3", "Model4", "Model5"],
            'PCMODEL': ["PcModel1", "PcModel2", "PcModel3", "PcModel4", "PcModel5"],
            'DISPLAY_MANUFACTURER': ["DisplayManufacturer1", "DisplayManufacturer2", "DisplayManufacturer3", "DisplayManufacturer4", "DisplayManufacturer5"],
            ######
            #Ricoh dictionary entries
            'RICOHMODEL': ["RicohModel1", "RicohModel2", "RicohModel3", "RicohModel4", "RicohModel5"],
            'DETAILEDMODEL': ["DetailedModel1", "DetailedModel2", "DetailedModel3", "DetailedModel4", "DetailedModel5"],
            'IB_CITY': ["IBCity1", "IBCity2", "IBCity3", "IBCity4", "IBCity5"],
            'LOCODE': ["LOCode1", "LOCode2", "LOCode3", "LOCode4", "LOCode5"],
            'OPERATION': ["Operation1", "Operation2", "Operation3", "Operation4", "Operation5"],
            ######
            #Zebra dictionary entries
            'OPERATIVA_ACTUAL': ["OperativaActual1", "OperativaActual2", "OperativaActual3", "OperativaActual4", "OperativaActual5"],
            'POBLACION': ["Poblacion1", "Poblacion2", "Poblacion3", "Poblacion4", "Poblacion5"],
            'DESCRIPCIÓN': ["Descripcion1", "Descripcion2", "Descripcion3", "Descripcion4", "Descripcion5"],
        }

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

        cursor = self.connection.cursor()
        cursor.execute(f"SHOW COLUMNS FROM {self.current_table}")
        column_names = [column[0] for column in cursor.fetchall()]

        # Generate widgets for each row
        self.generate_row_widgets(add_dialog, column_names, entries)
        # Add a button that adds the row when clicked
        Button(add_dialog, text="Add", command=lambda: self.insert_row([e.get() for e in entries])).grid(row=len(column_names), column=0, columnspan=2)
        
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

        # Create labels and entries for the column name and filter value
        Label(filter_dialog, text="Column name").grid(row=0, column=0)
        column_name_entry = Entry(filter_dialog)
        column_name_entry.grid(row=0, column=1)

        Label(filter_dialog, text="Filter value").grid(row=1, column=0)
        filter_value_entry = Entry(filter_dialog)
        filter_value_entry.grid(row=1, column=1)

        # Add a button that applies the filter when clicked
        Button(filter_dialog, text="Apply filter",
            command=lambda: self.filter_table(column_name_entry.get(), filter_value_entry.get())
            ).grid(row=2, column=0, columnspan=2)
        
    def refresh_table(self):
        self.change_table(self.current_table)




root = Tk()
app = DatabaseApp(root)
root.mainloop()

