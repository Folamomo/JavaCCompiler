/*mulitline
very long
comment*/
int b = -(10+~0x17);
const char* string_literal = "// /* string contents\t\r"
Struct S{
    int x;
    double y;
};

int testFunction(int* input, int length) {
  int sum=0;
  S s{1, 2};
  for (int i = 0; i<length; ++i) { //comment
    sum += input[i];
  }

  double a = 11.1;
  double b = 11.1320e20;
  double c = 11.1320e+20;
  double d = 11.1320e-20;
  double e = 0x10;
  double f = 0x10.11p-10;
  double g = 0x10.11p10;
  double h = 0x10.11p+10;
  return sum;
}
