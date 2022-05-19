# read file 
def deliveryline(filename):
    with open(filename, 'r') as file:
        newText = []
        i=1
        for line1 in file:
            with open(filename, 'r') as file2:
                for line2 in file2:
                    
                    try:
                        if(line1 != line2):
                            i+=1
                            newText.append("INSERT INTO POSTDELIVERY_LINE (ID, FROM_, TO_) VALUES ('"+str(i)+"', '"+line1.strip()+"', '"+line2.strip()+"');\n")
                    except ValueError:
                        pass
        with open("deliveryline.txt", 'w') as file:
            for line in newText:
                file.write(line + "\n")



def cities(filename):
    with open(filename, 'r') as file:
        # read line by line
        newText = []
        for line in file:
            try:
                newText.append("INSERT INTO POSTAVAIABLECITIES (NAMES) VALUES ('"+line[:-1]+"');\n")
            except ValueError:
                pass
        with open("Cities_.sql", 'w') as file:
            for line in newText:
                file.write(line + "\n")
from random import randint
import names
import random_address 




def gen_phone():
    first = str(randint(10,99))
    second = str(randint(1,888)).zfill(3)

    last = (str(randint(1,9998)).zfill(3))
    while last in ['1111','2222','3333','4444','5555','6666','7777','8888']:
        last = (str(randint(1,9998)).zfill(4))
        
    return '06{}-{}-{}'.format(first,second, last)

import os

def Createusers():
        newText = []
        fixer=2
        for i in range(2,226):
            if i !=fixer:
                id=randint(100000, 999999)
                Firstname = names.get_first_name()
                Lastname = names.get_last_name()
                address = random_address.real_random_address_by_state('CA')
                try:
                    newText.append("INSERT INTO POSTACCOUNT (EMAIL, PASSWORD, ID_USER, ACCOUNTTYPE) VALUES\n ('Abdobella"+str(i)+"@gmail.com', '$2a$10$fIHBbsXdnjsqpRKlLX.qle7yQ7VIKeaJRE8J042LyDXr2qsqw6mv2', 'JC"+str(id)+"', 'employee');\n")
                    
                    newText.append("INSERT INTO POSTEMPLOYEE (ID, EMAIL, COLUMN2, COLUMN3, COLUMN4, COLUMN5, COLUMN6, DELIVERYLINE_ID) VALUES\n ('JC"+str(id)+"', 'Abdobella"+str(i)+"@gmail.com', '"+Firstname+"', '"+Lastname+"', 'Marocain', '"+address["address1"]+"', '"+gen_phone()+"', "+str(i)+");\n")
                except ValueError:
                    pass  
            else:
                fixer+=16
                
        with open("users_.sql", 'w') as file:
            for line in newText:
                file.write(line + "\n")

# INSERT INTO POSTACCOUNT (EMAIL, PASSWORD, ID_USER, ACCOUNTTYPE) VALUES ('Abdobella1@gmail.com', '$2a$10$fIHBbsXdnjsqpRKlLX.qle7yQ7VIKeaJRE8J042LyDXr2qsqw6mv2', 'jc999999', 'employee')

# INSERT INTO POSTEMPLOYEE (ID, EMAIL, COLUMN2, COLUMN3, COLUMN4, COLUMN5, COLUMN6, DELIVERYLINE_ID) VALUES ('jc999999', 'Abdobella1@gmail.com', 'sdf', 'ujhy', 'hgf', 'sads', 'asd', '1')


cities("DB_proto/cities.txt")

# Createusers()