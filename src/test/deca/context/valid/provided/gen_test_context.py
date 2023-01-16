##
##
## Générateur de tests pour le compilateur Deca
##
## Usage: python3 test.py
##
## Fait par groupe GL32

import random

nb_test = 50 # nombre de tests à générer
extension = ".deca" # extension des fichiers de tests

for i in range(nb_test): # boucle sur le nombre de tests à générer
    file_name = "pythontest" + str(i) + extension
    with open(file_name, "w") as f:

        f.write("{")


            
            # La grammaire deca impose que l'on doit déclarer les variables avant de les utiliser dans le programme , et aucune instruction interférera avec la déclaration des variables
        for i in range(10):
            variable = random.randint(0,1);
            if variable == 0:
                f.write("int " + "var" + str(i) + " = "+str(random.randint(0, 8))+";\n")
            elif variable == 1:
                f.write("float " + "var" + str(i) + " = "+str(random.uniform(0, 8))+";\n")
       
                
        

            # On génère des instructions aléatoires pour le corps du programme deca avec des boucles pour augmenter la couverture de code
        for j in range(9):
            instruction = random.randint(0, 11)

            if instruction == 0:
                f.write("if (var" + str(i) + " == " + str(random.randint(0, 8)) + " || var" + str(j+1) + " != " + str(random.randint(0, 8)) + ") {\n")
                f.write("    println(\"" + random.choice(["Phelma", "Ensimag", "GL"]) + "\");\n")
                f.write("} else {\n")
                f.write("    println(var" + str(j+1) + ");\n")
                f.write("}\n")
            elif instruction == 1:
                f.write("while (var" + str(i) + " < " + str(random.randint(0, 8)) + " && var" + str(j+1) + " == " + str(random.randint(0, 8)) + ") {\n")
                f.write("    println(" + random.choice(["var" + str(i), "var" + str(j), "\"Phelma\"", "\"Ensimag\"", "\"GL\""]) + ");\n")
                f.write("}\n")
            elif instruction == 2:
                f.write("while (var" + str(i) + " > " + str(random.randint(0, 8)) + " && var" + str(j+1) + " == " + str(random.randint(0, 8)) + ") {\n")
                f.write("    println(" + random.choice(["var" + str(i), "var" + str(j), "\"Phelma\"", "\"Ensimag\"", "\"GL\""]) + ");\n")
                f.write("}\n")
            elif instruction == 3:
                f.write("while (var" + str(i) + " <= " + str(random.randint(0, 8)) + " && var" + str(j+1) + " == " + str(random.randint(0, 8)) + ") {\n")
                f.write("    println(" + random.choice(["var" + str(i), "var" + str(j), "\"Phelma\"", "\"Ensimag\"", "\"GL\""]) + ");\n")
                f.write("}\n")
            elif instruction == 4:
                f.write("while (var" + str(i) + " >= " + str(random.randint(0, 8)) + " && var" + str(j+1) + " == " + str(random.randint(0, 8)) + ") {\n")
                f.write("    println(" + random.choice(["var" + str(i), "var" + str(j), "\"Phelma\"", "\"Ensimag\"", "\"GL\""]) + ");\n")
                f.write("}\n")
            elif instruction == 5:
                f.write("while (var" + str(i) + " != " + str(random.randint(0, 8)) + " && var" + str(j+1) + " == " + str(random.randint(0, 8)) + ") {\n")
                f.write("    println(" + random.choice(["var" + str(i), "var" + str(j), "\"Phelma\"", "\"Ensimag\"", "\"GL\""]) + ");\n")
                f.write("}\n")
            elif instruction == 6:
                f.write("while (!(var" + str(i) + " != " + str(random.randint(0, 8)) + ')'+" && var" + str(j+1) + " == " + str(random.randint(0, 8)) + ") {\n")
                f.write("    println(" + random.choice(["var" + str(i), "var" + str(j), "\"Phelma\"", "\"Ensimag\"", "\"GL\""]) + ");\n")
                f.write("}\n")
            elif instruction == 7:
                f.write("println(var" + str(j) + ");\n")
            elif instruction == 8:
                f.write("println(\"" + random.choice(["Phelma", "Ensimag", "GL"]) + "\");\n")
            elif instruction == 9 and variable != 2 :
                f.write("println(" + str(random.randint(0, 8)) + ");\n")
            elif instruction == 10 and variable != 2 : 
                f.write("printlnx(" + str(random.uniform(0, 8)) + ");\n")
            elif instruction == 11 and variable != 2 :
                f.write("printx(" + str(random.uniform(0, 8)) + ");\n")

        f.write("}")

          


