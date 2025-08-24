import React, { useState } from "react";

const Login = () => {
  const [signUp, setSignUp] = useState(false);
  const [user, setUser] = useState("");
  const [pass, setPass] = useState("");
  const [warning, setWarning] = useState("");
  const [confirmPass, setConfirmPass] = useState("");

  const changeFormState = (e) => {
    e.preventDefault();
    setSignUp((prev) => !prev);
  };
  
  return (
    <div className="bg-[#33A1E0] h-full w-full">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <a
          href="#"
          className="flex items-center mb-6 text-2xl font-bold text-white"
        >
          Disaster relief System
        </a>
        <div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
              {signUp ? "Create your account" : "Sign In"}
            </h1>
            <form className="space-y-4 md:space-y-6" action="#">
              <div>
                <label
                  htmlFor="username"
                  className="block mb-2 text-sm font-medium text-gray-900"
                >
                  Your username
                </label>
                <input
                  type="text"
                  name="username"
                  id="username"
                  onChange={(e) => setUser(e.target.value)}
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                  placeholder="username"
                  required
                />
              </div>
              <div>
                <label
                  htmlFor="password"
                  className="block mb-2 text-sm font-medium text-gray-900"
                >
                  {signUp ? "Enter Password" : "Password"}
                </label>
                <input
                  type="password"
                  name="password"
                  id="password"
                  onChange={(e) => setPass(e.target.value)}
                  placeholder="••••••••"
                  className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                  required
                />
              </div>
              {signUp ? (
                <>
                  <div>
                    <label
                      htmlFor="password"
                      className="block mb-2 text-sm font-medium text-gray-900"
                    >
                      Confirm Password
                    </label>
                    <input
                      type="password"
                      name="password"
                      id="password"
                      onChange={(e) => setConfirmPass(e.target.value)}
                      placeholder="••••••••"
                      className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                      required
                    />
                  </div>
                </>
              ) : (
                ""
              )}
              {signUp ? (
                <>
                  <button
                    type="submit"
                    onClick={(e) => register(e)}
                    className="w-full text-white bg-[#33A1E0] hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                  >
                    Register
                  </button>
                </>
              ) : (
                <>
                  <button
                    onClick={(e) => login(e)}
                    type="submit"
                    className="w-full text-white bg-[#33A1E0] hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                  >
                    Sign In
                  </button>
                </>
              )}
              {warning ? (
                <>
                  <p className="text-sm font-normal text-red-600 p-0 m-0">
                    {warning}
                  </p>
                </>
              ) : (
                ""
              )}
              <p className="text-sm font-light text-gray-500">
                {signUp ? "Have an account. " : "Don’t have an account yet? "}
                <button
                  onClick={(e) => changeFormState(e)}
                  className="font-medium text-[#33A1E0] hover:underline"
                >
                  {signUp ? "Sign In" : "Sign up"}
                </button>
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
