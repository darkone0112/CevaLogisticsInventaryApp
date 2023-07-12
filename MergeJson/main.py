import json
import tkinter as tk
from tkinter import filedialog, ttk, messagebox

def load_json_file():
    file_path = filedialog.askopenfilename(filetypes=(("JSON files", "*.json"), ("All files", "*.*")))
    if not file_path:
        messagebox.showerror("Error", "No file selected.")
        return []
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

def merge_json(json1, json2, key):
    if not json1 or not json2:
        messagebox.showerror("Error", "Please load JSON files.")
        return []
    if not key:
        messagebox.showerror("Error", "Please select a key.")
        return []

    merged_dict = {item[key]: item for item in json1}
    for item in json2:
        if item[key] not in merged_dict.keys():
            merged_dict[item[key]] = item

    merged = list(merged_dict.values())
    return merged

def save_json_file(data, output_folder):
    output_file = output_folder + "/computers.json"
    with open(output_file, 'w',) as file:
        json.dump(data, file, indent=4)

def main():
    window = tk.Tk()

    frame = ttk.Frame(window)
    frame.grid(column=0, row=0, sticky=(tk.W, tk.E, tk.N, tk.S))

    ttk.Label(frame, text="Select Key").grid(column=1, row=1)
    key_var = tk.StringVar()
    key_entry = ttk.Combobox(frame, textvariable=key_var)
    key_entry.grid(column=2, row=1)

    json1 = []
    json2 = []
    output_folder = ''

    def load_jsons():
        nonlocal json1
        nonlocal json2
        json1 = load_json_file()
        json2 = load_json_file()
        if not json1 or not json2:
            return
        common_keys = set(json1[0].keys()).intersection(set(json2[0].keys()))  # assuming all dictionaries in the JSON objects have the same keys
        key_entry['values'] = list(common_keys)

    def select_output_folder():
        nonlocal output_folder
        output_folder = filedialog.askdirectory()
        if not output_folder:
            messagebox.showerror("Error", "No output folder selected.")

    def handle_submit():
        key = key_var.get()
        if not key:
            messagebox.showerror("Error", "Please select a key.")
            return
        duplicates1 = find_duplicates(json1, key)
        duplicates2 = find_duplicates(json2, key)
        if duplicates1:
            print("Duplicates found for key {} in JSON 1: {}".format(key, duplicates1))
            messagebox.showinfo("Duplicates Found", "Duplicates found for key {} in JSON 1. See console for details.".format(key))
        if duplicates2:
            print("Duplicates found for key {} in JSON 2: {}".format(key, duplicates2))
            messagebox.showinfo("Duplicates Found", "Duplicates found for key {} in JSON 2. See console for details.".format(key))
        merged = merge_json(json1, json2, key)
        save_json_file(merged, output_folder)
        print('Merge complete.')

    ttk.Button(frame, text="Load JSONs", command=load_jsons).grid(column=2, row=3)
    ttk.Button(frame, text="Select Output Folder", command=select_output_folder).grid(column=2, row=4)
    ttk.Button(frame, text="Submit", command=handle_submit).grid(column=2, row=5)

    window.mainloop()

if __name__ == "__main__":
    main()
