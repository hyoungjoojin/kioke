"use client";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { z } from "zod";
import { useTranslations } from "next-intl";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { signInWithCredentials } from "@/lib/auth/actions";
import { useState } from "react";
import { getErrorMessage } from "@/constants/errors";
import { redirect } from "next/navigation";
import { LoginFormSchema } from "@/lib/auth";

export default function LoginForm() {
  const t = useTranslations("");

  const [isError, setIsError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const loginForm = useForm<z.infer<typeof LoginFormSchema>>({
    resolver: zodResolver(LoginFormSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const formSubmitHandler = async (values: z.infer<typeof LoginFormSchema>) => {
    setIsError(false);
    const { success, code } = await signInWithCredentials(
      values.email,
      values.password,
    );

    if (success) {
      redirect("/");
    }

    if (!success && code) {
      setIsError(true);
      setErrorMessage(getErrorMessage(code));
    }
  };

  return (
    <Card className="w-[28rem] dark:bg-zinc-900">
      <CardHeader>
        <CardTitle>{t("login.title")}</CardTitle>
        <CardDescription>{t("login.description")}</CardDescription>
      </CardHeader>

      <CardContent>
        {isError && (
          <p className="text-[0.8rem] font-medium text-destructive">
            {t(errorMessage)}
          </p>
        )}
        <Form {...loginForm}>
          <form onSubmit={loginForm.handleSubmit(formSubmitHandler)}>
            <div className="my-3">
              <FormField
                name="email"
                control={loginForm.control}
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="flex justify-between items-center">
                      <div className="text-black dark:text-white">
                        {t("login.email.label")}
                      </div>
                      <FormMessage>
                        {t("login.email.invalid-email")}
                      </FormMessage>
                    </FormLabel>
                    <FormControl>
                      <Input
                        placeholder={t("login.email.placeholder")}
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
            </div>

            <div className="my-3">
              <FormField
                name="password"
                control={loginForm.control}
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="flex justify-between items-center">
                      <div className="text-black dark:text-white">
                        {t("login.password.label")}
                      </div>
                      <FormMessage>{t("login.password.empty")}</FormMessage>
                    </FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder={t("login.password.placeholder")}
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <div className="flex justify-end my-2">
                <span className="text-sm hover:underline hover:cursor-pointer">
                  {t("login.forgot-password")}
                </span>
              </div>
            </div>

            <div className="flex justify-center">
              <Button type="submit" className="w-full">
                {t("login.login-button")}
              </Button>
            </div>

            <div
              onClick={() => {
                redirect("/auth/register");
              }}
              className="flex justify-center my-2"
            >
              <span className="text-sm hover:underline hover:cursor-pointer">
                Not a member? Sign up now.
              </span>
            </div>
          </form>
        </Form>
      </CardContent>
    </Card>
  );
}
