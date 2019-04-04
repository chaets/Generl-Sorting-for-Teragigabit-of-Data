CC = javac
CFLAGS = 

clean:
	rm -rf 

all: clean
	$(CC) $(CFLAGS) sort2.java
	$(CC) $(CFLAGS) sort20.java


