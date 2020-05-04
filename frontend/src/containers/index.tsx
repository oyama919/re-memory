import { connect } from "react-redux";
import { countUp } from "../actions";
import App from "../components/App";

const mapStateToProps = (state) => ({
  count: state.countUp
});

const mapDispatchToProps = (dispatch) => ({
  countUpEvent: () => dispatch(countUp())
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
