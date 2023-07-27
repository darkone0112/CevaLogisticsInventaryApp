import json
import tkinter as tk
from tkinter import filedialog, ttk

def load_json_file():
    file_path = filedialog.askopenfilename(filetypes=(("JSON files", "*.json"), ("All files", "*.*")))
    with open(file_path, 'r') as file:
        data = json.load(file)
    return data, file_path

def save_json_file(data, file_path):
    with open(file_path, 'w',) as file:
        json.dump(data, file, indent=4)

def add_field(data, key):
    for item in data:
        if key not in item:
            item[key] = None
    return data

def main():
    window = tk.Tk()

    frame = ttk.Frame(window)
    frame.grid(column=0, row=0, sticky=(tk.W, tk.E, tk.N, tk.S))

    ttk.Label(frame, text="Select Key").grid(column=1, row=1)
    key_var = tk.StringVar()
    key_entry = ttk.Combobox(frame, textvariable=key_var)
    key_entry.grid(column=2, row=1)

    data = []
    file_path = ''

    def load_json():
        nonlocal data
        nonlocal file_path
        data, file_path = load_json_file()
        keys = set(data[0].keys())  # assuming all dictionaries in the JSON objects have the same keys
        key_entry['values'] = list(keys)

    def handle_submit():
        key = key_var.get()
        updated_data = add_field(data, key)
        save_json_file(updated_data, file_path)
        print('Process complete.')

    ttk.Button(frame, text="Load JSON", command=load_json).grid(column=2, row=3)
    ttk.Button(frame, text="Submit", command=handle_submit).grid(column=2, row=5)

    window.mainloop()

if __name__ == "__main__":
    main()
