"use client";
import LoginForm, {
  LoginFormSchema,
} from "@/components/pages/auth/login/LoginForm";
import { SelectLanguage } from "@/components/utils/i18n";
import { z } from "zod";
import { MainLogo } from "@/components/utils/logo";
import { ToggleDarkModeButton } from "@/components/utils/theme";
import { signInWithCredentials } from "@/lib/auth/actions";

const formSubmitHandler = async (values: z.infer<typeof LoginFormSchema>) => {
  signInWithCredentials(values);
};

export default function Login() {
  return (
    <div className="flex w-full h-dvh items-center justify-center bg-gray-50 dark:bg-black">
      <div className="absolute top-10 left-10">
        <MainLogo />
      </div>

      <div className="absolute bottom-10 left-10">
        <ToggleDarkModeButton />
      </div>

      <div className="absolute bottom-10 right-10">
        <SelectLanguage />
      </div>

      <LoginForm onSubmit={formSubmitHandler} />
    </div>
  );
}
