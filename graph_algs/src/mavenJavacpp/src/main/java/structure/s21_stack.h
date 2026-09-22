#pragma once
#include <cstddef>
#include <memory>
#include <stdexcept>
#include <utility>
namespace s21 {
template <typename T>
class stack {
 private:
  T *array;
  size_t quantity;

 public:
  using value_type = T;
  using reference = T &;
  using const_reference = const T &;
  using size_type = size_t;
  stack() : array(nullptr), quantity(0) {}
  ~stack() { delete[] array; }
  const_reference top() {
    if (this->quantity <= 0) {
      throw std::out_of_range("Stack is empty");
    }
    return *(this->array + (this->quantity - 1));
  }
  void push(const_reference value) {
    rewrite_stack();
    try {
      new (this->array + this->quantity - 1) T(value);
    } catch (...) {
      (this->array + this->quantity - 1)->~T();
      throw;
    }
  }
  void rewrite_stack() {
    value_type *tmp = new value_type[this->quantity + 1];
    if (this->quantity) {
      try {
        std::uninitialized_copy(this->array, this->array + this->quantity, tmp);
      } catch (...) {
        delete[] reinterpret_cast<char *>(tmp);
        throw;
      }
    }
    this->quantity += 1;
    delete[] this->array;
    this->array = tmp;
  }
  value_type pop() {
    if (this->quantity <= 0) {
      throw std::out_of_range("Stack is empty");
    }
    value_type result = *(this->array + (this->quantity - 1));
    (this->array + this->quantity - 1)->~T();
    this->quantity -= 1;
    return result;
  }
  bool empty() {
    return this->quantity == 0;
  }
};
}  // namespace s21
