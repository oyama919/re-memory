import React from 'react';
import PropTypes from 'prop-types';

function Greeting (props) {
  return (
    <div>
    {props.name}
    </div>
  );
}

Greeting.propTypes = {
  name: PropTypes.string.isRequired,
};

export default Greeting;