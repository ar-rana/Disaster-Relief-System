import React, { useState } from "react";

const LoginAndRegForm = ({ signUp, formHandler }) => {
  const [user, setUser] = useState("");
  const [pass, setPass] = useState("");
  const [contact, setContact] = useState("");
  const [warning, setWarning] = useState("");

  const handleSubmit = (e) => {
    if (!user || !pass) {
      setWarning("username or password missing");
      return;
    }
    if (signUp && !contact) {
      setWarning("contact missing");
      return;
    }
    setWarning("");
    
    e.preventDefault();
    formHandler(e);
  }
  return (
    <div>
      <div className="flex flex-col items-center justify-center px-4 py-6 mx-auto md:h-screen lg:py-0">
        <a className="flex items-center mb-4 text-2xl font-bold text-gray-800">
          Disaster relief System
        </a>
        <div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0">
          <div className="p-6 space-y-3 md:space-y-4 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
              {signUp ? "Register a provider" : "Sign In"}
            </h1>
            <form className="space-y-4 md:space-y-4">
              <div>
                <label
                  htmlFor="username"
                  className="block mb-2 text-sm font-medium text-gray-900"
                >
                  Username
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
                {signUp ? (
                  <>
                    <label
                      htmlFor="contact"
                      className="block mb-2 text-sm font-medium text-gray-900"
                    >
                      Contact number
                    </label>
                    <input
                      type="number"
                      name="contact"
                      id="contact"
                      onChange={(e) => setContact(e.target.value)}
                      className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                      placeholder="contact"
                      required
                    />
                  </>
                ) : (
                  ""
                )}
              </div>
              <div>
                <label
                  htmlFor="password"
                  className="block mb-2 text-sm font-medium text-gray-900"
                >
                  {signUp ? "Set Password" : "Password"}
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
                  <button
                    type="submit"
                    onClick={(e) => handleSubmit(e)}
                    className="w-full text-white bg-[#33A1E0] hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                  >
                    Register
                  </button>
                </>
              ) : (
                <>
                  <button
                    onClick={(e) => handleSubmit(e)}
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
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginAndRegForm;
