if [ -e "../bin" ]
then
    rm -rf ../bin
fi
mkdir ../bin

if [ -e "./ACTUAL.txt" ]
then
    rm ACTUAL.txt
fi

find .. -name "*.java" > sources.txt
if ! javac -Xlint:none -d ../bin @sources.txt
then
    echo "********** BUILD FAILURE **********"
    read -n 1 -s -r -p "Press any key to continue..."
    exit 1
fi
rm sources.txt

java -cp ../bin dook/Dook 412 . < input.txt > ACTUAL.txt
diff -u ACTUAL.txt EXPECTED.txt
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
else
    echo "Test result: FAILED"
fi
read -n 1 -s -r -p "Press any key to continue..."
