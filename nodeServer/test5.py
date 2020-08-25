
import pymysql
import csv

conn = pymysql.connect(host='localhost', user='root', password='hogwarts',
                       db='medicine_db', charset='utf8')

# Connection 으로부터 Cursor 생성
curs = conn.cursor()

f = open('./interaction.csv','r',encoding='euc-kr')
reader = csv.reader(f)
print(type(reader))

for line in reader:
    drug1 = line[3]
    drug2 = line[9]

    interaction = line[14]

    if(drug1.find("주")<0 and drug2.find("주")<0):
        a = drug1.find("(")
        b = drug2.find("(")
        if (a>0):
            drug1 = drug1[:a]
        if(b>0):
            drug2 = drug2[:b]
        drug1 = drug1.replace("밀리그람", "MG")
        drug1 = drug1.replace("밀리그램", "MG")
        drug2 = drug2.replace("밀리그람", "MG")
        drug2 = drug2.replace("밀리그램", "MG")
        drug1 = drug1.replace("_", "")
        drug2 = drug2.replace("_", "")

        table = "drug_interactions"  # 테이블명
        sql = "insert into " + table + ' (drug_name1,drug_name2,effect) values ("'+drug1+'","'+drug2+'","'+interaction + '");'
        #print(sql)
        curs.execute(sql)
        conn.commit()
        rows = curs.fetchall()

conn.close()
