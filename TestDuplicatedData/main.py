import json
import tkinter as tk
from tkinter import filedialog, ttk, messagebox

def load_json_file():
    file_path = filedialog.askopenfilename(filetypes=(("JSON files", "*.json"), ("All files", "*.*")))
    with open(file_path, 'r') as file:
        data = json.load(file)
    return data

def find_duplicates(data, key):
    value_counts = {}
    for item in data:
        value = item[key]
        if value in value_counts:
            value_counts[value] += 1
        else:
            value_counts[value] = 1
    duplicates = [value for value, count in value_counts.items() if count > 1]
    return duplicates

def main():
    window = tk.Tk()

    frame = ttk.Frame(window)
    frame.grid(column=0, row=0, sticky=(tk.W, tk.E, tk.N, tk.S))

    ttk.Label(frame, text="Select Key").grid(column=1, row=1)
    key_var = tk.StringVar()
    key_entry = ttk.Combobox(frame, textvariable=key_var)
    key_entry.grid(column=2, row=1)

    data = []

    def load_json():
        nonlocal data
        data = load_json_file()
        keys = list(data[0].keys())  # assuming all dictionaries in the JSON objects have the same keys
        key_entry['values'] = keys

    def handle_submit():
        key = key_var.get()
        duplicates = find_duplicates(data, key)
        if duplicates:
            print("Duplicates found for key {}: {}".format(key, duplicates))
            messagebox.showinfo("Duplicates Found", "Duplicates found for key {}. See console for details.".format(key))
        else:
            messagebox.showinfo("No Duplicates Found", "No duplicates found for key {}.".format(key))

    ttk.Button(frame, text="Load JSON", command=load_json).grid(column=2, row=2)
    ttk.Button(frame, text="Submit", command=handle_submit).grid(column=2, row=3)

    window.mainloop()

if __name__ == "__main__":
    main()
