import React, { useState, useEffect } from 'react';
import CheckoutForm from '../components/CheckoutForm';
import { getCartItems } from '../services/cartService';

const CheckoutPage = () => {
  const [cartItems, setCartItems] = useState([]);

  useEffect(() => {
    const fetchCart = async () => {
      const data = await getCartItems();
      setCartItems(data);
    };
    fetchCart();
  }, []);

  const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.qty, 0);

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-3xl font-bold mb-6">Checkout</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Cart Summary */}
        <div>
          <h2 className="text-2xl font-semibold mb-4">Order Summary</h2>
          <div className="space-y-4">
            {cartItems.map((item) => (
              <div
                key={item.id}
                className="flex justify-between bg-white p-4 rounded shadow"
              >
                <span>{item.name} x {item.qty}</span>
                <span>${item.price * item.qty}</span>
              </div>
            ))}
          </div>
          <div className="mt-4 text-right font-bold text-lg">
            Total: ${totalPrice}
          </div>
        </div>

        {/* Checkout Form */}
        <div>
          <CheckoutForm cartItems={cartItems} totalPrice={totalPrice} />
        </div>
      </div>
    </div>
  );
};

export default CheckoutPage;
