import tkinter as tk
from tkinter import messagebox
import subprocess
import sys
import os
import glob

class Application(tk.Frame):
    def __init__(self, master=None):
        super().__init__(master)
        self.master = master
        self.pack()
        self.create_widgets()
        

    def create_widgets(self):
        self.label = tk.Label(self, text="Select a program to run:")
        self.label.pack(pady=10)
        #self.root.geometry = ('300x200')
        # Search for Python scripts in subdirectories
        self.files = glob.glob('**/*.py', recursive=True)

        self.listbox = tk.Listbox(self)
        for file in self.files:
            self.listbox.insert(tk.END, file)
        self.listbox.pack(pady=10)
        self.run_button = tk.Button(self)
        self.run_button["text"] = "Run Selected Program"
        self.run_button["command"] = self.run_selected_program
        self.run_button.pack(pady=10)

    def run_selected_program(self):
        selection = self.listbox.curselection()
        if selection:
            file = self.files[selection[0]]
            try:
                subprocess.Popen([sys.executable, file])
            except Exception as e:
                messagebox.showerror("Error", str(e))
        else:
            messagebox.showinfo("Information", "No file selected")

def center_window(root, width=300, height=300):
    # get screen width and height
    screen_width = root.winfo_screenwidth()
    screen_height = root.winfo_screenheight()

    # calculate position x and y coordinates
    x = (screen_width/2) - (width/2)
    y = (screen_height/2) - (height/2)
    root.geometry('%dx%d+%d+%d' % (width, height, x, y))

root = tk.Tk()
root.title("Program Launcher")
center_window(root) # you can adjust width and height if needed
app = Application(master=root)
app.mainloop()
