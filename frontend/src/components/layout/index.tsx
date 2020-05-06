import React, { ReactNode } from 'react';
import Header from './header';

type Props = {
    children: ReactNode
}

const Layout = (props: Props): JSX.Element => {
  return (
    <>
        <Header />
        {props.children}
    </>
  );
}

export default Layout;
