import React from 'react';
import { Link } from "react-router-dom";

const Header = (): JSX.Element => {
  return (
    <header>
      <ul>
        <li><Link to="/">home</Link></li>
        <li><Link to="/login">login</Link></li>
      </ul>
    </header>
  );
}

export default Header;
