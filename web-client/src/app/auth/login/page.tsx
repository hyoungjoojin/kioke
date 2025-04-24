"use client";

import LoginForm from "./components/LoginForm";
import { SelectLanguage } from "@/components/utils/i18n";
import { MainLogo } from "@/components/utils/logo";
import { ToggleDarkModeButton } from "@/components/utils/theme";

export default function Login() {
  return (
    <>
      <main className="flex w-full h-dvh items-center justify-center bg-gray-50 dark:bg-black">
        <div className="absolute top-10 left-10">
          <MainLogo />
        </div>

        <div className="absolute bottom-10 left-10">
          <ToggleDarkModeButton />
        </div>

        <div className="absolute bottom-10 right-10">
          <SelectLanguage />
        </div>

        <LoginForm />
      </main>
    </>
  );
}
