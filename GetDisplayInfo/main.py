import time
import wmi
import pandas as pd
from pathlib import Path

def get_monitors_info():
    c = wmi.WMI(namespace="root\wmi")
    monitors_info = []
    for monitor in c.WmiMonitorID():
        try:
            name = ''.join([chr(i) for i in monitor.UserFriendlyName if i]) if monitor.UserFriendlyName else "Not Found"
            serial = ''.join([chr(i) for i in monitor.SerialNumberID if i]) if monitor.SerialNumberID else "Not Found"
            monitors_info.append({"Model": name, "Serial": serial})
        except Exception as e:
            print(f"Error retrieving monitor info: {e}")
    return monitors_info

def append_to_excel(excel_file, new_data):
    # Check if the Excel file exists
    if Path(excel_file).is_file():
        # Read the existing data
        df_existing = pd.read_excel(excel_file)
        # Convert the new data to DataFrame
        df_new = pd.DataFrame(new_data)
        # Append the new data
        df_combined = pd.concat([df_existing, df_new], ignore_index=True).drop_duplicates()
    else:
        # If the file doesn't exist, just use new data
        df_combined = pd.DataFrame(new_data)
    
    # Write the combined data to Excel file
    df_combined.to_excel(excel_file, index=False)
    print(f"Monitor info updated in {excel_file}.")

def monitor_changes(excel_file, polling_interval=5):
    previous_monitors = []
    
    while True:
        current_monitors = get_monitors_info()
        if current_monitors != previous_monitors:
            new_monitors = [m for m in current_monitors if m not in previous_monitors]
            if new_monitors:
                print("New monitor detected:")
                for monitor in new_monitors:
                    print(f"Model: {monitor['Model']}, Serial: {monitor['Serial']}")

                append_to_excel(excel_file, new_monitors)
            
            previous_monitors = current_monitors
        time.sleep(polling_interval)

if __name__ == "__main__":
    excel_file = "C:\\Users\\menendezj\\Desktop\\Repos\\CevaLogisticsInventaryApp\\GetDisplayInfo\\monitors_info.xlsx"
    monitor_changes(excel_file)
