import json
from tkinter import filedialog, StringVar, Tk, Label, Entry, Button, OptionMenu, CENTER, W

def open_file():
    file_path = filedialog.askopenfilename(filetypes=(("JSON files", "*.json"), ("All files", "*.*")))
    with open(file_path, 'r') as file:
        data = json.load(file)
    return data, file_path

def replace_data(old_data, new_data, key, data):
    for dic in data:
        if dic[key] == old_data:
            dic[key] = new_data
    return data

def write_to_file(file_path, data):
    with open(file_path, 'w') as file:
        json.dump(data, file, indent=4)

def create_gui(data, file_path):
    root = Tk()
    root.geometry("300x200")
    root.title('JSON Data Modifier')

    # Set window to center of screen
    window_width = root.winfo_reqwidth()
    window_height = root.winfo_reqheight()
    position_right = int(root.winfo_screenwidth()/2 - window_width/2)
    position_down = int(root.winfo_screenheight()/2 - window_height/2)
    root.geometry("+{}+{}".format(position_right, position_down))

    # Assuming all dictionaries have same keys
    keys = list(data[0].keys())
    keys_var = StringVar(root)
    keys_var.set(keys[0])  # default value

    old_data_var = StringVar(root)
    old_data_var.set(data[0][keys[0]])  # default value

    def update_old_data(*args):
        key = keys_var.get()
        old_data_var.set(data[0][key])  # set default value to first occurrence
        menu = old_data_option['menu']
        menu.delete(0, 'end')
        new_choices = set(dic[key] for dic in data)
        for choice in new_choices:
            menu.add_command(label=choice, command=lambda choice=choice: old_data_var.set(choice))

    keys_var.trace('w', update_old_data)

    keys_option = OptionMenu(root, keys_var, *keys)
    keys_option.pack()

    label_old_data = Label(root, text="Old Data")
    label_old_data.pack()

    old_data_option = OptionMenu(root, old_data_var, *[dic[keys[0]] for dic in data])
    old_data_option.pack()

    label_new_data = Label(root, text="New Data")
    label_new_data.pack()
    entry_new_data = Entry(root)
    entry_new_data.pack()

    def handle_submit():
        key = keys_var.get()
        old_data = old_data_var.get()
        new_data = entry_new_data.get()
        new_data = replace_data(old_data, new_data, key, data)
        write_to_file(file_path, new_data)
        print('Data replacement done.')
        root.destroy()
        create_gui(new_data, file_path)

    submit_button = Button(root, text="Submit", command=handle_submit)
    submit_button.pack()

    root.mainloop()

def main():
    data, file_path = open_file()
    create_gui(data, file_path)

if __name__ == "__main__":
    main()
