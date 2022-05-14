# read file 
def textsliceTool(filename):
    with open(filename, 'r') as file:
        # read line by line
        newText = []
        for line in file:
            # slice from index 0 to index with value ","
            try:
                # print(line[0:line.index(",")])
                # replcae Nfs with NTS in line
                # newText.append(line.replace('"NFS".', ""))
                newText.append(line.replace('NFS.', ""))
            except ValueError:
                pass
        # # write to file
        # with open("newText.txt", 'w') as newFile:
        #     for line in newText:
        #         newFile.write(line + "\n")
        # over write file with new text
        with open(filename, 'w') as file:
            for line in newText:
                file.write(line + "\n")

                
textsliceTool("C:/Users/Abdelouahab/Downloads/sql4")