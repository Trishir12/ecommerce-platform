import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => (
  <nav className="bg-white shadow-md p-4 flex justify-between">
    <h1 className="font-bold text-xl text-blue-600">E-Shop</h1>
    <div className="space-x-4">
      <Link to="/cart" className="text-gray-700 hover:text-blue-600">Cart</Link>
      <Link to="/orders" className="text-gray-700 hover:text-blue-600">Orders</Link>
    </div>
  </nav>
);

export default Navbar;
