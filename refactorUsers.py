# Define the tables
table1 = [
    "Cano, Adolfo", "Martin, Alberto Lorenzo", "Martinez Pineda, Alberto",
    "Soto Cantillo, Alvaro Junior", "Peinado Oliva, Daniel", "Fernandez, Gema",
    "Otarola, Guillermo", "Del Corral, Juan Francisco", "Madrigal Barchino, Roberto",
    "Gomes Orlando, Rosanna", "Centenera, Sonia", "erkazan, yagmur"
]

table2 = [
    "Agata Polis", "Alberto Martinez Pineda", "Alvaro Junior Soto Cantillo",
    "Aydin Tasdelen", "Cristobal Nebot Lopez", "Diego Salinas Ortega",
    "Guillermo Gomez Rodriguez", "Jorge Paredes Chaves", "Juan Francisco Del Corral",
    "Juan Maria Hernandez Gonzalez", "Luis Rozas Elias", "Maria del Carmen Rodriguez Suarez",
    "Miguel Angel Yanez", "Miguel Garcia Cintas", "Pinar Kir", "Roberto Madrigal Barchino",
    "Rosanna Gomes Orlando"
]

# Helper function to convert name formats
def convert_name(name):
    parts = name.split(", ")
    if len(parts) == 2:
        # Format from Table 1 to match Table 2
        return f"{parts[1]} {parts[0]}"
    return name

# Convert Table 1 names to match Table 2 format
table1_converted = [convert_name(name) for name in table1]

# Users in Table 1 but not in Table 2
users_in_1_not_in_2 = [name for name in table1_converted if name not in table2]

print("Users in Table 1 but not in Table 2:")
for user in users_in_1_not_in_2:
    print(user)
