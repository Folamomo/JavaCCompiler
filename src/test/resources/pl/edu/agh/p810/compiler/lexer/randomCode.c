/*mulitline
very long
comment*/
const char* string_literal = "// /* string contents\t\r"
int testFunction(int* input, int length) {

  int sum=0;
  for (int i = 0; i<length; ++i) { //comment
    sum += input[i];
  }
  return sum;
}
