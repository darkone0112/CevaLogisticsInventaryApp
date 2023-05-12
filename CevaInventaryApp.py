from tkinter import simpledialog
import mysql.connector
from mysql.connector import Error
from tkinter import *
from tkinter import messagebox, ttk

class DatabaseApp:
    def __init__(self, root):
        self.root = root
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
        
        self.table_menu.add_command(label='beta barajas', command=lambda: self.change_table('beta_inventory_barajas'))
        self.table_menu.add_command(label='ricoh', command=lambda: self.change_table('ricoh'))
        self.table_menu.add_command(label='zebraont', command=lambda: self.change_table('zebraont'))

        # Initialize currently selected table and row
        self.current_table = None
        self.current_row = None

        # Create Treeview
        style = ttk.Style()
        style.configure("Treeview", background="#002147", foreground="white", fieldbackground="#002147 ")  # Set treeview colors
        style.configure("Treeview.Heading", background="#002147", foreground="black")  # Set treeview heading colors
        self.tree = ttk.Treeview(self.root, style="Treeview", height=700)
        self.tree.pack()

        # Bind row select event
        self.tree.bind('<<TreeviewSelect>>', self.on_row_selected)
        
        
        #Display default table
        self.change_table('ricoh')
        
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
                self.tree.column(col, width=120, anchor="center")
        else:
            # Otherwise, create a new configuration
            self.column_configurations[table_name] = {}
            for col in column_names:
                self.tree.column(col, width=120, anchor="center")  # adjust the width to suit your needs
                self.column_configurations[table_name][col] = 120  # Store the width

        # Set column titles for each column
        for col in column_names:
            self.tree.heading(col, text=col)

        # Insert new data
        for row in cursor.fetchall():
            self.tree.insert('', 'end', values=row)
            
        # Hide the first column 
        self.tree.column('id', width=0, stretch=NO)



    def on_row_selected(self, event):
        selected_items = self.tree.selection()  # get selected items
        if selected_items:  # check if there are any selected items
            selected_item = selected_items[0]
            self.current_row = self.tree.item(selected_item)['values']


    def delete_row(self):
        if self.current_row:
            cursor = self.connection.cursor()
            cursor.execute(f"DELETE FROM {self.current_table} WHERE id = {self.current_row[0]}") # Assuming the first column is the ID
            self.connection.commit()
            self.change_table(self.current_table)  # Refresh table

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

        # Clear treeview
        for i in self.tree.get_children():
            self.tree.delete(i)

        # Insert new data
                # Insert new data
        for row in cursor.fetchall():
            self.tree.insert('', 'end', values=row)
    def show_context_menu(self, event):
    # Select row under mouse
        self.tree.identify_row(event.y)
        self.context_menu.post(event.x_root, event.y_root)

    def update_row_dialog(self):
        # Create a new Toplevel window
        update_dialog = Toplevel(self.root)
        update_dialog.title("Update row")

        # Create an Entry for each field in the row, except 'id'
        entries = [self.current_row[0]]  # start with 'id' value
        for i, value in enumerate(self.current_row[1:], start=1):  # starting from 1
            Label(update_dialog, text=f"Field {i}").grid(row=i-1, column=0)
            entry = Entry(update_dialog)
            entry.grid(row=i-1, column=1)
            entry.insert(0, value)
            entries.append(entry)

        # Add a button that updates the row when clicked
        Button(update_dialog, text="Update", command=lambda: self.update_row([e.get() if isinstance(e, Entry) else e for e in entries])).grid(row=len(self.current_row)-1, column=0, columnspan=2)





root = Tk()
app = DatabaseApp(root)
root.mainloop()

