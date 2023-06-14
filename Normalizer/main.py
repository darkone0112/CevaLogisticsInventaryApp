import tkinter as tk
from tkinter import filedialog
import json
from fuzzywuzzy import process

def load_json():
    filename = filedialog.askopenfilename(filetypes=[("JSON files", "*.json")])
    if filename:
        with open(filename, 'r') as file:
            data = json.load(file)
        return data
    return None

def load_main_json():
    global main_data
    main_data = load_json()

def load_group_json():
    global group_data
    group_data = load_json()

def process_data():
    global main_data, group_data

    if main_data is None or group_data is None:
        print("Please load both JSON files before processing.")
        return

    # Creating a list for easy lookup
    group_list = [item['PcModel'] for item in group_data]

    for item in main_data:
        model = item['PcModel'].lower().strip()
        best_match = process.extractOne(model, group_list)
        if best_match:
            # Replace with the best matching model
            item['PcModel'] = best_match[0]

    # Save the processed data
    with open('processed_data.json', 'w') as file:
        json.dump(main_data, file, indent=4)

    print("Processing completed. Data saved to 'processed_data.json'.")


main_data = None
group_data = None

root = tk.Tk()
tk.Button(root, text='Load Main JSON', command=load_main_json).pack()
tk.Button(root, text='Load Group JSON', command=load_group_json).pack()
tk.Button(root, text='Process', command=process_data).pack()

root.mainloop()
