import tkinter as tk
from tkinter import messagebox
import os

class MyMainProgram:
    def __init__(self):
        # Initialization logic here
        pass

    def run(self):
        # Main program logic here
        print("Running another Python script...")
        os.system("python CevaInventaryApp.py") # replace with your actual script path

def check_version():
    # Map the network drive
    path = os.system(r"net use Z: \\esoga01vwtfs01\Tools")

    print(path)
    with open("Z:\\Ceva-Inventory-App\\version.txt", "r") as file2:
        version2 = file2.read().strip()
        
    return "1" == version2

def start_program(root):
    # Map the network drive
    os.system(r"net use Z: \\esoga01vwtfs01\Tools")
    
    # Check version condition
    if check_version():
        # Show message box
        messagebox.showinfo("Info", "Welcome to the Ceva-Invetory-App!")
        program = MyMainProgram()
        program.run()
        # Close tkinter window
        root.destroy()
    else:
        print("Version mismatch, program will not run.")

    # Unmap the network drive
    os.system("net use Z: /delete /yes")

def create_gui():
    root = tk.Tk()

    check_button = tk.Button(root, text="Check version and start program", command=lambda: start_program(root))
    check_button.pack()

    root.mainloop()

create_gui()
