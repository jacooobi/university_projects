#include <iostream>
#include <cstdlib>
#include <algorithm>
#include <time.h>
#include "node.h"
#include "bst.h"

using namespace std;
void generate_array(int *array, int size, int min, int max)
{
    srand((unsigned) time(0));
    for(int i = 0; i < size; i++) {
        array[i] = min + (rand() % (max - min + 1));
    }
}

int rand_interval(int min, int max)
{
    srand((unsigned) time(0));
    return min + (rand() % (max - min + 1));
}

void test(int arr_size, int min, int max)
{
    BST *tree = new BST();
    
    int array[100];
    generate_array(array, arr_size, min, max);

    tree->from_array(array, arr_size);
    tree->balance();
    
    cout << "Min: " << tree->min()->value << endl;
    
    int how_many, key;
    
    tree->print_sorted();
    
    cin >> how_many;
    for(int i = 0; i < how_many; i++) {
        cin >> key;
        tree->remove(key);
    }
    
    cout << "Sorted: " << endl;
    tree->print_sorted();
    cout << endl;
    
    cout << "Cleaning tree ..." << endl;
    tree->clean();
    cout << "Tree clear" << endl;
    
    tree->print_root();
}

double get_result(double *arr, int size)
{
    double res = 0;
    for(int i = 0; i < size; i++) {
        res += arr[i];
    }
    
    return (res / size);
}

double get_minus(double *arr, int size)
{
    double res = arr[0];
    
    for(int i = 1; i < size; i++) {
        if(arr[i] < res) res = arr[i];
    }
    
    return res;
}

double get_plus(double *arr, int size)
{
    double res = arr[0];
    
    for(int i = 1; i < size; i++) {
        if(arr[i] > res) res = arr[i];
    }
    
    return res;
}

void benchmark()
{
    BST *tree = new BST();
    int min_val = 10;
    int array[10] = {5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000};
    for(int i = 0; i < 10; i++) {
        int arr[array[i]];
        generate_array(arr, array[i], min_val, array[i]);
        sort(arr, arr + array[i], greater<int>());
        
        tree->from_array(arr, array[i]);
        double results[3];
        clock_t clock_start;
        for(int j = 0; j < 3; j++) {
            clock_start = clock();
            
            tree->print_sorted();
//            tree->min();
//            tree->from_array(arr, array[i]);
            
            results[j] = (double) (clock() - clock_start)/CLOCKS_PER_SEC;
        }
        double result = get_result(results, 3);
        double minus = result - get_minus(results, 3);
        double plus = get_plus(results, 3) - result;
        
//        cout << (int) (result * 1000) << "\t" << (int) (minus * 1000) << "\t" << (int) (plus * 1000) << endl;
        cout << result * 1000000 << "\t" << minus * 1000000 << "\t" << plus * 1000000 << endl;
        
        tree->clean();
    }
}

int main()
{
//    test(10, 1, 100);
    benchmark();
    return 0;
}
