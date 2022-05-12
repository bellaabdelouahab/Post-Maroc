# read file 
def textsliceTool(filename):
    with open(filename, 'r') as file:
        # read line by line
        newText = []
        for line in file:
            # slice from index 0 to index with value ","
            try:
                print(line[0:line.index(",")])
                newText.append(line[0:line.index(",")])
            except ValueError:
                pass
        # write to file
        with open("newText.txt", 'w') as newFile:
            for line in newText:
                newFile.write(line + "\n")
                
textsliceTool("cities.txt")