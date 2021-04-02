/*
李浩  201810420117   网络工程  Visual Studio
功能
********************************************
	 1，登录系统
		学生进入借阅系统
		管理员进入管理系统

		图书管理
		  管理员	  添加                   图书的基本信息，如 书号，书名，作者，出版社，类别，进库量,价格；
					  删除                   图书，通过图书的书号或书名进行删除；
					  查看					 查看学生图书借阅情况

		  学生	      借还书登记             先判断是否有这本书，归还截止期限，借书书名进行登记；
			          个人还书情况查看       个人借阅情况查看；

		  共用        查询                   图书通过图书的书号，书名，作者进行检索；
					  浏览                   图书的库存，将所有数目展示出来。
		   
	 2，创建账号
		可创建学生账号
		可创建管理员账号(必须输入我给的指定的密码才可以)
	 3，修改密码
		先输入账号
		再更改密码
	 4，退出系统
********************************************
执行过程：注册账号，会创建一个文件，内含账号信息。登录时会形成一个链表，依次匹配你输入的账号密码，成功后进入因进入的界面，然后继续执行操作，添加书等都会创建文件储存;
修改密码会先将文件导成链表，修改后，从头将链表再次导入文件，取代原文件全部内容。
		  图书操作也类似，均为从文件导入形成链表，操作后也会写入文件
核心：数据在文件与链表的相互转换；二进制文件内容与文本形式输出间的转换
*/

#include <stdio.h>
#include <stdlib.h>//其中包含system函数
#include <conio.h>//定义了通过控制台进行 数据输入 和数据输出的函数，如getch函数
#include <string.h>//定义字符数组
#include <math.h>
#include<malloc.h>//空间分配
#include<Windows.h>
#include<time.h>//时间使用

char books[30] = "libraryinformation.txt";  //书籍信息
char guanlipeople[40] = "adminastrationinformation.txt"; //管理信息储存
char students[40] = "studentsinformation.txt";//学生信息储存
char studentandbook[32] = "student of book information.txt";//学生借书信息储存
char choice;//选择按键
char studentnumone[17];//学生登录后记录学生账号作为学生唯一标识
int key = 123456;//我给的密码


//图书信息
struct book
{
	char num[20], name[20], aut[20], pub[20]; //书号，书名，作者，出版社
	int cat;//类别
	int  many;//进库量
	int  kucun;//库存
	double price;//价格
};
//图书链表
struct booknode
{
	struct book book;
	struct booknode *next;
};
//借阅信息
struct borrow
{
	char studentnum[17];//借书号，即学生登录账号
	struct tm timetime;//储存借书时间
	struct book book;//所借图书信息
};
//借阅信息链表
struct borrownode
{
	struct borrow borrow;
	struct borrownode*next;
};
//登录信息
struct land//登录信息
{
	char zhanghao[17];//账号
	char password[17];//密码
};
//登录链表
struct node
{
	struct land land;
	struct node*next;
};

//共用
int displaymenu();//展示主菜单
void browse(struct booknode *);//浏览所有图书
void selectinformation();//查询通过关键信息
void beginregister(char*, char*);//新建账号
void enter(char*, char*,char*);//登录  ——》》  主要操作
void updatekey(char*, char*);//更新密码
void timedisplay();//时间展示
void displayonebook(struct booknode*);//单本书展示
void studentoneborrowshow(struct borrownode*head);//单个学生借书情况查看
struct node *initDate1();//初始化管理登录
struct node *initDate2();//初始化学生登录
struct node *importData(FILE *fp);//导入数据文件中的数据
struct booknode *initDatebook();//初始化图书信息
struct booknode *importDatabook(FILE *fp);//导入图书信息
struct borrownode*initDataborrow();//初始化借阅信息
struct borrownode*importDataborrow(FILE*fp);//导入借阅信息
//管理
int displayguanli();//展示管理界面
void addbooks(char *);//添加图书
void drop(char*books);//删除图书
void chakanstudent();//查看学生借阅情况
//学生
int displaystudents();//展示学生界面
void borrowreturn();//借书操作
void studentshow();//学生借还书查看


void main()
{
	/*
	while (1)
	{
		//system("color 70");//白背景黑字体
		displaymenu();//展示主菜单
		switch (choice)
		{
		case '1':
			enter(guanlipeople, students,books);//登录操作
			break;
		case '2':
			beginregister(guanlipeople, students);//注册操作
			break;
		case '3':
			updatekey(guanlipeople, students);//更改密码
			break;
		case '4':exit(0);//退出系统
			break;
		default:
			printf("\n选择有误请重输\n");
			break;
		}
	}
	*/
	int i = 1;
	i = i++;
	printf("%d\n",i);
	
	system("pause");
}


//共用
//主菜单
int displaymenu()
{
	while (1)
	{
		timedisplay();
		printf("\n\t\t\t欢迎使用图书馆登陆系统\n");
		printf("************************************************************\n");
		printf("\t\t\t1  登录系统\n\t\t\t\t学生进入借阅系统\n\t\t\t\t管理员进入管理系统\n");
		printf("\t\t\t2  创建账号\n\t\t\t\t可创建学生账号\n\t\t\t\t可创建管理员账号\n");
		printf("\t\t\t\t若没有账号请创建一个，若有则可登录\n\t\t\t\t不要创建已有账号,创建不了\n");
		printf("\t\t\t3  修改密码\n\t\t\t\t先输入账号\n\t\t\t\t再更改密码\n");
		printf("\t\t\t4  退出系统\n");
		printf("\n\t\t密码账号均以16为限\n");
		printf("\n\t\t因为错误操作处理不完全所以请按照提示操作\n\n\t\t在应当输入数字时请勿输入汉字或者字母\n");
		printf("************************************************************\n");
		printf("请输入你的选择：");
		choice = getche();
		if (1 > (choice - '0') || (choice - '0') > 4)
		{
			printf("\n输入有误，请重输\n");
			system("pause");
			rewind(stdin);
		}
		else return choice;
	}
}
//浏览图书信息
void browse(struct booknode*head)
{
	struct booknode*p;
	while (head != NULL)
	{
		printf("\n书号为：%s\n书名为：%s\n作者：%s\n出版社：%s\n类别：%d\n库存量为：%d\n价格：%lf\n",head->book.num,head->book.name,head->book.aut,head->book.pub,head->book.cat,head->book.kucun,head->book.price);
		p=head->next ;
		head = p;
	}
	//另一种直接对文件操作
	/*FILE *fp;
	struct book book;
	fp = fopen(books, "r");
	while (fread(&book,sizeof(struct book),1,fp)==1)
	{
		printf("\n书号为：%s\n书名为：%s\n作者：%s\n出版社：%s\n类别：%d\n库存量为：%d\n价格：%lf\n",head->book.num,head->book.name,head->book.aut,head->book.pub,head->book.cat,head->book.kucun,head->book.price);
	}*/
}
//查询图书信息
void selectinformation()
{
	printf("\n欢迎进入图书查询功能\n您将可以通过图书的书号，书名，作者进行检索\n");
	printf("\n(1)书号(2)书名(3)作者\n");
	choice = getche();
	if (1 > (choice - '0') || (choice - '0') > 3)
	{
		printf("\n输入有误，请重输\n");
		system("pause");
		rewind(stdin);
	}
	//根据书号查询
	if (choice == '1')
	{
		int judge = 1;
		struct booknode *head, *p;
		struct book book;
		head = initDatebook();//导入书籍信息
		printf("\n请输入图书书号：\n");
		scanf("%s", book.num);
		while (head != NULL)
		{
			if (strcmp(head->book.num, book.num) == 0)
			{
				printf("\n已经匹配到图书\n");
				displayonebook(head);
				judge = 0;
			}
			p = head->next;
			head = p;
		}
		free(head);
		if (judge == 1)
			printf("\n未找到图书信息\n");
	}
	//根据书名查询
	if (choice == '2')
	{
		int judge = 1;
		struct booknode *head, *p;
		struct book book;
		head = initDatebook();//导入书籍信息
		printf("\n请输入图书书名：\n");
		scanf("%s", book.name);
		while (head != NULL)
		{
			if (strcmp(head->book.name, book.name) == 0)
			{
				printf("\n已经匹配到图书\n");
				displayonebook(head);
				judge = 0;
			}
			p = head->next;
			head = p;
		}
		free(head);
		if (judge == 1)
			printf("\n未找到图书信息\n");
	}
	//根据作者查询
	if (choice == '3')
	{
		int judge = 1;
		struct booknode *head, *p;
		struct book book;
		head = initDatebook();//导入书籍信息
		printf("\n请输入图书作者：\n");
		scanf("%s", book.aut);
		while (head != NULL)
		{
			if (strcmp(head->book.aut, book.aut) == 0)
			{
				printf("\n已经匹配到图书\n");
				displayonebook(head);
				judge = 0;
			}
			p = head->next;
			head = p;
		}
		free(head);
		if (judge == 1)
			printf("\n未找到图书信息\n");
	}
}
//新建文件（账号）
//检查账号是否存在，不存在才能注册
void beginregister(char*guanli, char*students)
{
	FILE *fp; int select; struct land land;
	int flag = 1;
	int guanlizhuru;
	char zhanghao[17]; char password[17];

	//注册账号
	while (flag)
	{
		printf("\n管理和学生任何一个文件不存在均需要再来一次\n账号密码尽量不要太长（退出输入$）\n");
		printf("输入账号：");
		scanf("%s", zhanghao);
		strcpy(land.zhanghao, zhanghao);
		//退出
		if (strcmp(zhanghao, "$") == 0)
		{
			printf("\n返回上级功能\n");
			return;
		}
		//判断账号是否已经存在

		int flag2;
		struct node *head, *p;
		head = initDate1();//导入管理信息文件形成链表
		while (head != NULL)
		{
			flag2 = 1;
			flag2 = strcmp(head->land.zhanghao, land.zhanghao);
			if (flag2 == 0)
			{
				printf("\n账号已经存在！请重新选择\n");
				return;
			}
			p = head->next;
			head = p;
		}

		int flag3;
		struct node *head2, *p2;
		head2 = initDate2();//导入学生信息文件形成链表
		while (head2 != NULL)
		{
			flag3 = 1;
			flag3 = strcmp(head2->land.zhanghao, land.zhanghao);
			if (flag3 == 0)
			{
				printf("\n账号已经存在！请重新选择\n");
				return;
			}
			p2 = head2->next;
			head2 = p2;
		}

		//如果账号不存在继续运行
		printf("输入密码：");
		scanf("%s", password);
		strcpy(land.password, password);
		printf("如果想要设为管理则需要输入密码（我给定的一个密码）\n");
		printf("\n你想把它设为管理还是学生？\n输入1为管理\n输入2为学生\n");
		select = getche();
		switch (select)
		{
		case '1':
			printf("\n请输入我给的密码：");
			scanf("%d", &guanlizhuru);
			if (key == guanlizhuru)
			{
				if ((fp = fopen(guanli, "r")) == NULL)
				{
					fp = fopen(guanli, "w");
					fclose(fp);
				}
				fclose(fp);
				fp = fopen(guanli, "a");
				fwrite(&land, sizeof(struct land), 1, fp);
				fclose(fp);
				printf("注册成功\n");
				flag = 0;
			}
			else printf("\n密码错误\n");
			break;
		case '2':
			if ((fp = fopen(students, "r")) == NULL)
			{
				fp = fopen(students, "w");
				fclose(fp);
			}
			fclose(fp);
			fp = fopen(students, "a");
			fwrite(&land, sizeof(struct land), 1, fp);
			fclose(fp);
			printf("注册成功\n");
			flag = 0;
			break;
		default:printf("\n输入错误，请重新输入\n"); break;
		}rewind(stdin);
	}
}
//登录验证——》》  主要操作
//判断账号密码是否正确，正确进入管理（学生）操作界面
void enter(char*guanli, char*students,char*books)
{
	char zhanghao[17];
	char mima[17];
	char choose;
	int flag;//是否进行判断密码
	int judge = 1;//0为登录成功
	printf("\n请输入账号密码\n");
	printf("账号:");
	scanf("%s", zhanghao);
	printf("密码:");
	scanf("%s", mima);
	rewind(stdin);
	struct land lander;
	strcpy(lander.zhanghao, zhanghao);
	strcpy(lander.password, mima);
	printf("你输入的是管理还是学生？\n (1)管理员\t(2)学生\n");
	scanf("%c", &choose);

	//管理登录
	if (choose == '1')
	{
		//判断账号密码是否正确
		struct node *head, *p;
		//导入登录信息头指针
		head = initDate1();
		while (head != NULL)
		{
			flag = strcmp(head->land.zhanghao, lander.zhanghao);
			if (flag == 0)
			{
				flag = strcmp(head->land.password, lander.password);
			}
			if (flag == 0) judge = 0;
			p = head->next;
			head = p;
		}
		free(head);


		if (judge == 0)
		{
			//进入管理操作界面
			int judge2 = 1;
			printf("\n登录成功\n欢迎进入管理界面\n");
			while (judge2)
			{
				judge2 = 1;
				struct booknode*head;
				head = initDatebook();
				//导入图书信息头指针
				displayguanli();
				switch (choice)
				{
				case '1':addbooks(books);
					break;
				case '2':drop(books);
					break;
				case '3':selectinformation();
					break;
				case '4':browse(head);
					break;
				case'5':chakanstudent();
					break;
				case'6':
					judge2 = 0;
					break;
				default:
					printf("\n操作有误\n");
					break;
				}
				free(head);
			}
		}
		else
			printf("登录失败\n");
	}

	//学生登录
	if (choose == '2')
	{
		//判断账号密码是否正确
		struct node *head, *p;
		head = initDate2();
		int judge2 = 1;//0记录学生账号
		while (head != NULL)
		{
			flag = strcmp(head->land.zhanghao, lander.zhanghao);
			if (flag == 0)
			{
				flag = strcmp(head->land.password, lander.password);
			}
			if (flag == 0) 
			{
				judge = 0; judge2 = 0;
			}

			//账号密码匹配后记录账号
			if (judge2 == 0)
			{
				strcpy(studentnumone, head->land.zhanghao);
			}
			judge2 = 1;
			p = head->next;
			head = p;
		}
		free(head);


		if (judge == 0)
		{
			//进入学生操作界面
			int judge3 = 1;
			printf("\n登录成功\n欢迎进入学生界面\n");
			while (judge3)
			{
				judge3= 1;
				struct booknode*head;
				head = initDatebook();
				//导入图书信息头指针
				displaystudents();
				switch (choice)
				{
				case '1':borrowreturn();
					break;
				case '2':studentshow();
					break;
				case '3':selectinformation();
					break;
				case '4':browse(head);
					break;
				case'5':
					judge3 = 0;
					break;
				default:
					printf("\n操作有误\n");
					break;
				}
				free(head);
			}
		}
		else
			printf("登录失败\n");
	}

}
//更改密码
//检测账号密码是否正确，正确才可以更改
void updatekey(char*guanli, char*students)
{
	char select;
	printf("\n欢迎进入修改密码选项\n");
	printf("请输入你要修改的是管理员还是学生：\n(1)管理员\t(2)学生\n");
	scanf("%c", &select);
	if (select == '1')
	{
		//更改密码
		struct land lander;
		printf("\n请输入账号：");
		scanf("%s", lander.zhanghao);
		printf("\n请输入原来的密码：");
		scanf("%s", lander.password);
		int flag, judge = 1;
		struct node *head, *headtou, *p;
		head = initDate1();//导入管理信息文件
		headtou = head;//记录头位置
		while (head != NULL)
		{
			flag = 1;
			flag = strcmp(head->land.zhanghao, lander.zhanghao);
			if (flag == 0)
			{
				flag = strcmp(head->land.password, lander.password);
			}
			//账号密码均正确
			while (flag == 0)
			{
				printf("\n原账号密码正确\n请输入新的密码:");
				rewind(stdin);
				scanf("%s", head->land.password);//修改密码
				//将整个链表写入文件
				FILE*fp;
				fp = fopen(guanlipeople, "w");
				while (headtou != NULL)
				{
					fwrite(&headtou->land, sizeof(struct land), 1, fp);//将一条记录写入数据文件 
					headtou = headtou->next;
				}
				fclose(fp);
				printf("\n修改成功\n");
				return;
			}
			p = head->next;
			head = p;
		}
		printf("\n账号或者密码错误！");
	}
	if (select == '2')
	{
		//更改密码
		struct land lander;
		printf("\n请输入账号：");
		scanf("%s", lander.zhanghao);
		printf("\n请输入原来的密码：");
		scanf("%s", lander.password);
		int flag, judge = 1;
		struct node *head, *headtou, *p;
		head = initDate2();//导入管理信息文件
		headtou = head;//记录头位置
		while (head != NULL)
		{
			flag = 1;
			flag = strcmp(head->land.zhanghao, lander.zhanghao);
			if (flag == 0)
			{
				flag = strcmp(head->land.password, lander.password);
			}
			//账号密码均正确
			while (flag == 0)
			{
				printf("\n原账号密码正确\n请输入新的密码:");
				rewind(stdin);
				scanf("%s", head->land.password);//修改密码
				//将整个链表写入文件
				FILE*fp;
				fp = fopen(students, "w");
				while (headtou != NULL)
				{
					fwrite(&headtou->land, sizeof(struct land), 1, fp);//将一条记录写入数据文件 
					headtou = headtou->next;
				}
				fclose(fp);
				printf("\n修改成功\n");
				return;
			}
			p = head->next;
			head = p;
		}
		printf("\n账号或者密码错误！");
	}
	printf("\n输入错误，退出此功能模块\n");
}
//时间展示
void timedisplay()
{
	struct tm timetime;
	time_t now = time(NULL);//得到距1900年的秒数
	timetime = *localtime(&now);//将其化为日期时间格式存入结构体中
	printf("\n现在的时间是：%d年%d月%d日%d:%d:%d\n",timetime.tm_year+1900,timetime.tm_mon+1,timetime.tm_mday,timetime.tm_hour,timetime.tm_min,timetime.tm_sec );
}
//单本书展示
void displayonebook(struct booknode*head)
{
	printf("\n书号为：%s\n书名为：%s\n作者：%s\n出版社：%s\n类别：%d\n库存量为：%d\n价格：%lf\n", head->book.num, head->book.name, head->book.aut, head->book.pub, head->book.cat, head->book.kucun, head->book.price);
}
//单个学生借书情况查看
void studentoneborrowshow(struct borrownode*head)
{
	printf("\n\n学生号为：%s ", head->borrow.studentnum);
	printf("\n图书号为：%s", head->borrow.book.num);
	printf("\n图书名为：%s", head->borrow.book.name);
	printf("\n作者为：%s", head->borrow.book.aut);
	printf("\n出版社为：%s", head->borrow.book.pub);
	printf("\n图书类型为：【1】理科【2】文科【3】理科工具【4】文科工具【5】百科\n%d", head->borrow.book.cat);
	printf("\n单价为：%lf", head->borrow.book.price);
	printf("\n借书年份：%d", head->borrow.timetime.tm_year + 1900);
	printf("\t月份：%d", head->borrow.timetime.tm_mon + 1);
	printf("\t日期：%d", head->borrow.timetime.tm_mday);
}


//管理
//添加图书信息
void addbooks(char *books)
{
	FILE*fp;
	int flag = 1;
	//获得选择
	while (flag)
	{
		printf("\n（1）新加图书（2）增加库存\n应输入数字时请不要输入英文，否则会出现不可预知的错误!\n已有图书可增加库存，不可新加否则会重复\n");
		printf("请输入你的选择：");
		choice = getche();
		if (1 > (choice - '0') || (choice - '0') > 2)
		{
			printf("\n输入有误，请重输\n");
			system("pause");
			rewind(stdin);
		}
		else flag = 0;
	}

	//新加图书
	if (choice == '1')
	{
		struct book book;
		while (1)
		{
			printf("\n\n\n新加图书功能\n图书号若为$则退出此功能\n请输入图书基本信息\n");
			printf("(1)图书号：\n");
			scanf("%s", book.num);
			if (strcmp(book.num, "$") == 0)
			{
				printf("\n返回上级功能\n");
				return;
			}
			printf("(2)图书名：\n");
			scanf("%s", book.name);
			printf("(3)作者名字：\n");
			scanf("%s", book.aut);
			printf("(4)出版社：\n");
			scanf("%s", book.pub);
			printf("(5)类别: \n【1】理科【2】文科【3】理科工具【4】文科工具【5】百科\n");
			scanf("%d", &book.cat);
			printf("(6)进库量：\n");
			scanf("%d", &book.many);
			book.kucun = book.many;
			printf("(7)库存: \n%d\n ",book.kucun);

			//printf("\n%d\n",&book.kucun);
			//加了&就会变成一个很大的无规律数

			printf("(8)单价：\n");
			scanf("%lf", &book.price);

			fp = fopen(books, "a");
			fwrite(&book, sizeof(struct book), 1, fp);
			fclose(fp);
			printf("\n已经写入一本书！\n");
			rewind(stdin);
		}
	}

	//添加库存
	if (choice == '2')
	{
		struct book book;
		while (1)
		{
			struct booknode *head;
			head = initDatebook();//导入图书信息头文件,每次保证头在这个位置
			struct booknode*p, *headlocal;//headlocal定位首位地址 p循环使用 
			headlocal = head;
			printf("\n\n\n增加图书库存功能\n图书号若为$则退出此功能\n请输入图书基本信息\n");
			printf("(1)图书号：\n");
			scanf("%s", book.num);
			//退出
			if (strcmp(book.num, "$") == 0)
			{
				printf("\n返回上级功能\n");
				return;
			}
			//寻找图书
			flag = 1;
			while (head != NULL && flag != 0)
			{
				flag = strcmp(head->book.num, book.num);
				if (flag == 0)
				{
					printf("\n图书已找到，存在\n(6)进库量：\n");
					scanf("%d", &book.many);
					//进库量加到链表中的节点库存
					head->book.kucun =head->book.kucun + book.many;
					printf("(7)现库存:%d\n", head->book.kucun);
					//写入文件
					fp = fopen(books, "w");
					while (headlocal != NULL)
					{
						fwrite(headlocal, sizeof(struct book), 1, fp);
						p = headlocal->next;
						headlocal = p;
					}
					fclose(fp);
					printf("\n已经写入记录！\n");
					rewind(stdin);
				}
				p = head->next;
				head = p;
			}
			if (head == NULL&&flag!=0)
				printf("\n未找到此图书\n");
			free(head);
		}
	}
}
//删除图书信息
void drop(char *books)
{
	while (1)
	{
		printf("\n欢迎进入删除图书功能\n\n图书号若为$则退出此功能\n（删除的图书是第一本的话没有确认操作）\n是否先浏览一下图书库存？（1）是\t(2)否\n");
		choice = getche();
		if (1 > (choice - '0') || (choice - '0') > 2)
		{
			printf("\n输入有误，请重输\n");
			system("pause");
			rewind(stdin);
		}
		//先浏览一下图书
		if (choice == '1')
		{
			struct booknode *head;
			head = initDatebook();
			browse(head);
			free(head);
		}
		//删除图书功能
		if (choice == '2')
		{
			struct book book;
			printf("\n删除图书：\n先输入你想删除的图书号：");
			scanf("%s", book.num);
			//退出
			if (strcmp(book.num, "$") == 0)
			{
				printf("\n返回上级功能\n");
				return;
			}
			//删除的整体结构
			int flag2 = 1;
			struct booknode *head, *p, *q;
			head = initDatebook();
			//如果已经没有了,链表为空，直接返回
			if (head == NULL)
			{
				printf("\n链表为空,没有书籍信息\n");
				return;
			}
			p = head;
			q = p->next;
			while (q != NULL)
			{
				//找到这本书
				if (strcmp(q->book.num, book.num) == 0)
				{
					flag2 = 0;
					//以防错误操作
					printf("\n图书号已匹配\n是否删除这本图书\n(1)是(2)否\n");
					choice = getche();
					if (1 > (choice - '0') || (choice - '0') > 2)
					{
						printf("输入有误，请重输\n");
						system("pause");
						rewind(stdin);
					}
					if (choice == '1')
					{
						//删除图书实现
						p->next = q->next;
						free(q);
						q = p->next;
						printf("\n已经删除这本图书！\n");
					}
					if (choice == '2')
					{
						printf("\n已取消删除\n");
					}
				}
				else
				{
					p = p->next;
					q = q->next;
				}
			}
			//如果就是头节点
			if (head != NULL && 0 == strcmp(head->book.num, book.num))
			{
				q = head;
				head = q->next;
				free(q);
				printf("\n已经找到，已经删除这本图书！\n");
				flag2 = 0;
			}
			//写入文件
			FILE*fp;
			fp = fopen(books, "w");
			while (head != NULL)
			{
				fwrite(head, sizeof(struct book), 1, fp);
				p = head->next;
				head = p;
			}
			fclose(fp);
			//没找到
			if (flag2 == 1)
				printf("\n未找到这本图书\n");

		}
	}
}
//展示管理菜单
int displayguanli()
{
	while (1)
	{
		timedisplay();
		printf("\n*************************************************************\n");
		printf("\n\t\t\t欢迎进入管理界面\n");
		printf("\t\t\t(1)添加图书信息\n\t\t\t\t（库存添加输入为负则减少库存）\n");
		printf("\t\t\t(2)删除图书信息\n");
		printf("\t\t\t(3)查询图书（根据书名，作者名等）\n");
		printf("\t\t\t(4)浏览图书全部信息\n");
		printf("\t\t\t(5)查阅学生借书情况\n");
		printf("\t\t\t(6)返回上级目录\n");
		printf("**************************************************************\n");
		choice = getche();
		if (1 > (choice - '0') || (choice - '0') > 6)
		{
			printf("\n输入有误，请重输\n");
			system("pause");
			rewind(stdin);
		}
		else return choice;
	}
}
//查看学生借阅情况
void chakanstudent()
{
	printf("\n欢迎查看学生的借阅情况\n");
	while (1)
	{
		printf("\n（输入$退出）\n请输入学生学号：\n");
		struct borrownode*head, *p;
		char xuehao[17];
		scanf("%s", xuehao);
		//退出
		if (xuehao[0] == '$')
		{
			printf("\n退出查看功能\n");
			return;
		}
		head = initDataborrow();
		int flag = 1;//flag=0则输出借阅信息
		while (head != NULL)
		{
			flag = strcmp(head->borrow.studentnum, xuehao);
			if (flag == 0)
			{
				studentoneborrowshow(head);
			}
			p = head->next;
			head = p;
		}
	}
}


//学生
//学生借还书查看
void studentshow()
{
	printf("\n欢迎进入学生个人借还书查看界面\n");
	printf("\n你的借书情况：\n");
	struct borrownode *head, *p;
	head = initDataborrow();
	int flag = 1;
	while (head != NULL)
	{
		flag=strcmp(head->borrow.studentnum, studentnumone);
		if (flag == 0)                                          
		{
			studentoneborrowshow(head);
		}
		p = head->next;
		head = p;
	}
}
//展示学生操作菜单
int displaystudents()
{
	while (1)
	{
		timedisplay();
		printf("\n****************************************************************\n");
		printf("\t\t\t欢迎进入学生界面\n");
		printf("\t\t\t(1)借还书操作\n");
		printf("\t\t\t(2)查看借还书情况\n");
		printf("\t\t\t(3)查询图书（根据书名，作者名等）\n");
		printf("\t\t\t(4)浏览图书全部信息\n");
		printf("\t\t\t(5)返回上级目录\n");
		printf("****************************************************************\n");
		choice = getche();
		if (1 > (choice - '0') || (choice - '0') > 5)
		{
			printf("\n输入有误，请重输\n");
			system("pause");
			rewind(stdin);
		}
		else return choice;
	}
}
//借还书操作
void borrowreturn()
{
	printf("\n欢迎进入学生借还书查看界面\n（1）借书（2）还书\n");
	printf("请输入你的选择：");
	choice = getche();
	if (1 > (choice - '0') || (choice - '0') > 2)
	{
		printf("\n输入有误，请重输\n");
		system("pause");
		rewind(stdin);
	}
	//借书
	if (choice == '1')
	{
		struct book bookk;//储存输入的书名或书号
		int flag = 1;//循环标志
		while (flag)
		{
			char choice2;//（1）书号或者（2）书名
			printf("\n请问你想根据书号还是书名借书：\n（1）书号（2）书名\n (3)退出借书功能\n");
			choice2 = getche();
			if (1 > (choice2 - '0') || (choice2 - '0') > 3)
			{
				printf("\n输入有误，请重输\n");
				system("pause");
				rewind(stdin);
			}
			//根据书号
			if (choice2 == '1')
			{
				struct booknode *head, *headtou, *headtou2, *p = NULL;
				int flag2 = 1, judge = 1;//judge=0找到书籍
				head = initDatebook();
				headtou = head;
				headtou2 = head;
				printf("\n请输入你想借的图书书号\n");
				scanf("%s", bookk.num);
				while (head != NULL)
				{
					flag2 = strcmp(head->book.num, bookk.num);//找到图书
					if (flag2 == 0)
					{
						judge = 0;
						printf("\n已找到你想借的图书\n");
						displayonebook(head);
						//确认操作
						printf("\n是否要借这本书：（1）是（2）否\n");
						choice = getche();
						if (1 > (choice2 - '0') || (choice2 - '0') > 2)
						{
							printf("\n输入有误，请重输\n");
							system("pause");
							rewind(stdin);
						}
						//借书实现
						if (choice == '1')
						{
							//库存减一
							head->book.kucun = head->book.kucun - 1;
							//写入文件,书籍库存减一
							FILE*fp;
							fp = fopen(books, "w");
							while (headtou != NULL)
							{
								fwrite(headtou, sizeof(struct book), 1, fp);
								p = headtou->next;
								headtou = p;
							}
							fclose(fp);
							headtou = headtou2;
							//时间得到
							struct tm timetime;
							time_t now = time(NULL);//得到距1900年的秒数
							timetime = *localtime(&now);//将其化为日期时间格式存入结构体中
							//学生借书信息加一
							struct borrow borrow;
							strcpy(borrow.studentnum, &studentnumone);
							strcpy(borrow.book.num, head->book.num);
							strcpy(borrow.book.name, head->book.name);
							strcpy(borrow.book.aut, head->book.aut);
							strcpy(borrow.book.pub, head->book.pub);
							borrow.book.cat = head->book.cat;
							borrow.book.price = head->book.price;
							borrow.timetime.tm_year = timetime.tm_year;
							borrow.timetime.tm_mon = timetime.tm_mon;
							borrow.timetime.tm_mday = timetime.tm_mday;

							//写入文件，借阅信息加一
							FILE*fp2;
							fp2 = fopen(studentandbook, "a");
							fwrite(&borrow, sizeof(struct borrow), 1, fp2);
							fclose(fp2);
							printf("\n借书成功\n");
						}
						//取消
						if (choice == '2')
						{
							printf("\n已经取消\n");
						}
					}
					p = head->next;
					head = p;
				}
				if (judge != 0)
				{
					printf("\n未找到此图书\n");
				}
				choice = NULL;
			}
			//根据书名
			if (choice2 == '2')
			{
				struct booknode *head, *headtou, *headtou2, *p;
				int flag2 = 1, judge = 1;//judge=0找到书籍
				head = initDatebook();
				headtou = head;
				headtou2 = head;
				printf("\n请输入你想借的图书书名\n");
				scanf("%s", bookk.name);
				while (head != NULL)
				{
					flag2 = strcmp(head->book.name, bookk.name);//找到图书
					if (flag2 == 0)
					{
						judge = 0;
						printf("\n已找到你想借的图书\n");
						displayonebook(head);
						//确认操作
						printf("\n是否要借这本书：（1）是（2）否\n");
						choice = getche();
						if (1 > (choice2 - '0') || (choice2 - '0') > 2)
						{
							printf("\n输入有误，请重输\n");
							system("pause");
							rewind(stdin);
						}
						//借书实现
						if (choice == '1')
						{
							//库存减一
							head->book.kucun = head->book.kucun - 1;
							//写入文件,书籍库存减一
							FILE*fp;
							fp = fopen(books, "w");
							while (headtou != NULL)
							{
								fwrite(headtou, sizeof(struct book), 1, fp);
								p = headtou->next;
								headtou = p;
							}
							fclose(fp);
							headtou = headtou2;
							//时间得到
							struct tm timetime;
							time_t now = time(NULL);//得到距1900年的秒数
							timetime = *localtime(&now);//将其化为日期时间格式存入结构体中
							//学生借书信息加一
							struct borrow borrow;
							strcpy(borrow.studentnum, &studentnumone);
							strcpy(borrow.book.num, head->book.num);
							strcpy(borrow.book.name, head->book.name);
							strcpy(borrow.book.aut, head->book.aut);
							strcpy(borrow.book.pub, head->book.pub);
							borrow.book.cat = head->book.cat;
							borrow.book.price = head->book.price;
							borrow.timetime.tm_year = timetime.tm_year;
							borrow.timetime.tm_mon = timetime.tm_mon;
							borrow.timetime.tm_mday = timetime.tm_mday;

							//写入文件，借阅信息加一
							FILE*fp2;
							fp2 = fopen(studentandbook, "a");
							fwrite(&borrow, sizeof(struct borrow), 1, fp2);
							fclose(fp2);
							printf("\n借书成功\n");
						}
						//取消
						if (choice == '2')
						{
							printf("\n已经取消\n");
						}
					}
					p = head->next;
					head = p;
				}
				if (judge != 0)
				{
					printf("\n未找到此图书\n");
				}
				choice = NULL;
			}
			//退出借书功能
			if (choice2 == '3')
			{
				flag = 0;
			}
		}
	}
	//还书
	if (choice == '2')
	{
		while (1)
		{
			printf("\n还书只能根据图书号操作\n还书成功会将图书书名改为（已还书籍）\n您的账号为：%s\n（退出借还书操作请输入$）\n还书请输入图书书号：", &studentnumone);
			char bookkk[17];
			scanf("%s", bookkk);
			//退出
			if (bookkk[0]== '$' )
			{
				printf("\n退出借还书操作\n");
				return;
			}

			struct booknode *head, *headtou, *p;
			struct borrownode*head2, *headtou2, *p2;
			int flag = 1;//0从借阅信息中找到与当前账号有关的借书信息结构体
			int flag2 = 1;//0找到借阅文件文件中账号中的图书号
			int flag3 = 1;//0图书文件中找到
			int flagg = 1;//是否输出“未找到图书”
			char huanshu[9] = "已还书籍";
			head = initDatebook();//图书信息
			head2 = initDataborrow();//借阅信息
			headtou = head;
			headtou2 = head2;
			while (head2 != NULL)
			{
				//从借阅信息中找到与当前账号有关的借书信息结构体
				flag = strcmp(head2->borrow.studentnum, studentnumone);
				if (flag == 0)
				{
					//找到借阅文件文件中账号中的图书号
					flag2 = strcmp(head2->borrow.book.num, bookkk);
					if (flag2 == 0)
					{
						//还书实现
						strcpy(head2->borrow.book.name, huanshu);
						//图书库存加一
						while (head != NULL)
						{
							flag3 = strcmp(head->book.num, bookkk);
							if (flag3 == 0)
							{
								head->book.kucun = head->book.kucun + 1;
								flagg = 0;//全部修改实现,不输出后面那段话
							}
							p = head->next;
							head = p;
						}
					}
				}
				p2 = head2->next;
				head2 = p2;
			}
			//写入借阅信息文件
			FILE*fp2;
			fp2 = fopen(studentandbook, "w");
			while (headtou2 != NULL)
			{
				fwrite(headtou2, sizeof(struct borrow), 1, fp2);
				p2 = headtou2->next;
				headtou2 = p2;
			}
			fclose(fp2);
			//写入图书信息文件
			FILE*fp;
			fp = fopen(books, "w");
			while (headtou != NULL)
			{
				fwrite(headtou, sizeof(struct book), 1, fp);
				p = headtou->next;
				headtou = p;
			}
			fclose(fp);

			//没找到
			if (flagg == 1)
				printf("\n未找到这本图书\n");
			if (flagg == 0)
				printf("\n已经还书成功\n");
		}
	}
}


//导入数据文件中的数据
struct node *importData(FILE *fp)
{
	struct node*head = NULL, *p, *q;//head指向链表头;p指向新节点; //q指向链表最后一个节点 
	struct land land;//用来登录的临时变量 
	fread(&land, sizeof(struct land), 1, fp);//从数据文件中读取一个账号信息 
	while (1 != feof(fp))          //如果文件没有结束,继续循环 
	{
		if (head == NULL)//单链表的第一个节点的处理 
		{
			//分配节点空间 
			head = (struct node*)malloc(sizeof(struct node));
			head->land = land;//把从文件中读入的记录,赋给链表第一个节点 
			head->next = NULL;//把节点的后继指针置NULL 
			q = head;
		}
		else//非第一个节点的处理
		{
			p = (struct node*)malloc(sizeof(struct node));
			p->land = land;
			p->next = NULL;
			q->next = p; // 链接新节点到表尾 
			q = p; //把q指针指向新节点
		}
		fread(&land, sizeof(struct land), 1, fp);// 从数据文件中读取下一条记录 
	}return head;//返回单链表的表头指针 
}
//初始化管理员登录系统1
struct node *initDate1()
{
	FILE*fp;
	struct node *land;
	// 如果第1次使用系统打开文件不成功 
	if ((fp = fopen(guanlipeople, " r")) == NULL)
	{
		fp = fopen(guanlipeople, "w");
		fclose(fp);
		printf("\n成功新建一个文件！请先退出系统，再执行操作！\n");
		exit(0);
	}
	else      //如果数据文件存在,直接导入数据 
		land = importData(fp);//导入已有文件的数据 
	fclose(fp);//关闭文件 
	return land;//返回存储数据的单链表头 
}
//初始化学生登录系统
struct node *initDate2()
{
	FILE*fp;
	struct node *land;
	// 如果第1次使用系统打开文件不成功 
	if ((fp = fopen(students, " r")) == NULL)
	{
		fp = fopen(students, "w");
		fclose(fp);
		printf("\n成功新建一个文件！请先退出系统，再执行操作！\n");
		exit(0);
	}
	else      //如果数据文件存在,直接导入数据 
		land = importData(fp);//导入已有文件的数据 
	fclose(fp);//关闭文件 
	return land;//返回存储数据的单链表头 
}


//初始化图书信息
struct booknode *initDatebook()
{
	FILE*fp;
	struct booknode *book;
	// 如果第1次使用系统打开文件不成功 
	if ((fp = fopen(books, " r")) == NULL)
	{
		fp = fopen(books, "w");
		fclose(fp);
		printf("\n(技术不成熟)成功新建一个图书信息文件！请先退出系统，重新运行，再执行操作！\n");
		exit(0);
	}
	else      //如果数据文件存在,直接导入数据 
		book = importDatabook(fp);//导入已有文件的数据 
	fclose(fp);//关闭文件 
	return book;//返回存储数据的单链表头 
}
//导入图书文件中的数据
struct booknode *importDatabook(FILE *fp)
{
	struct booknode*head = NULL, *p, *q;//head指向链表头;p指向新节点; //q指向链表最后一个节点 
	struct book book;//用来登录的临时变量 
	fread(&book, sizeof(struct book), 1, fp);//从数据文件中读取一个账号信息 
	while (1 != feof(fp))          //如果文件没有结束,继续循环 
	{
		if (head == NULL)//单链表的第一个节点的处理 
		{
			//分配节点空间 
			head = (struct booknode*)malloc(sizeof(struct booknode));
			head->book = book;//把从文件中读入的记录,赋给链表第一个节点 
			head->next = NULL;//把节点的后继指针置NULL 
			q = head;
		}
		else//非第一个节点的处理
		{
			p = (struct booknode*)malloc(sizeof(struct booknode));
			p->book = book;
			p->next = NULL;
			q->next = p; // 链接新节点到表尾 
			q = p; //把q指针指向新节点
		}
		fread(&book, sizeof(struct book), 1, fp);// 从数据文件中读取下一条记录 
	}return head;//返回单链表的表头指针 
}


//初始化借阅信息
struct borrownode*initDataborrow()
{
	FILE*fp;
	struct borrownode *studentborrow;
	// 如果第1次使用系统打开文件不成功 
	if ((fp = fopen(studentandbook, " r")) == NULL)
	{
		fp = fopen(studentandbook, "w");
		fclose(fp);
		printf("\n(技术不成熟)成功新建一个借阅信息文件！刚才的操作并未保存！请先退出系统，重新运行，再执行操作！\n");
		exit(0);
	}
	else      //如果数据文件存在,直接导入数据 
		studentborrow = importDataborrow(fp);//导入已有文件的数据 
	fclose(fp);//关闭文件 
	return studentborrow;//返回存储数据的单链表头 
}
//导入借阅信息
struct borrownode*importDataborrow(FILE*fp)
{
	struct borrownode*head = NULL, *p, *q;//head指向链表头;p指向新节点; //q指向链表最后一个节点 
	struct borrow borrow;//用来登录的临时变量 
	fread(&borrow, sizeof(struct borrow), 1, fp);//从数据文件中读取一个账号信息 
	while (1 != feof(fp))          //如果文件没有结束,继续循环 
	{
		if (head == NULL)//单链表的第一个节点的处理 
		{
			//分配节点空间 
			head = (struct borrownode*)malloc(sizeof(struct borrownode));
			head->borrow= borrow;//把从文件中读入的记录,赋给链表第一个节点 
			head->next = NULL;//把节点的后继指针置NULL 
			q = head;
		}
		else//非第一个节点的处理
		{
			p = (struct borrownode*)malloc(sizeof(struct borrownode));
			p->borrow = borrow;
			p->next = NULL;
			q->next = p; // 链接新节点到表尾 
			q = p; //把q指针指向新节点
		}
		fread(&borrow, sizeof(struct borrow), 1, fp);// 从数据文件中读取下一条记录 
	}return head;//返回单链表的表头指针 
}
