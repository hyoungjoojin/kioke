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

export const LoginFormSchema = z.object({
  email: z.string().email({ message: "Invalid email address." }),
  password: z.string().nonempty({ message: "Password is empty." }),
});

type LoginFormProps = {
  onSubmit(credentials: z.infer<typeof LoginFormSchema>): void;
};

export default function LoginForm({ onSubmit }: LoginFormProps) {
  const t = useTranslations("Login");

  const loginForm = useForm<z.infer<typeof LoginFormSchema>>({
    resolver: zodResolver(LoginFormSchema),
    defaultValues: {
      email: "hyoungjoo.j@gmail.com",
      password: "1234",
    },
  });

  return (
    <Card className="w-[28rem] dark:bg-zinc-900">
      <CardHeader>
        <CardTitle>{t("title")}</CardTitle>
        <CardDescription>{t("description")}</CardDescription>
      </CardHeader>

      <CardContent>
        <Form {...loginForm}>
          <form onSubmit={loginForm.handleSubmit(onSubmit)}>
            <div className="my-3">
              <FormField
                name="email"
                control={loginForm.control}
                render={({ field }) => (
                  <FormItem>
                    <FormLabel className="flex justify-between items-center">
                      <div className="text-black dark:text-white">
                        {t("email.label")}
                      </div>
                      <FormMessage />
                    </FormLabel>
                    <FormControl>
                      <Input placeholder={t("email.placeholder")} {...field} />
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
                        {t("password.label")}
                      </div>
                      <FormMessage />
                    </FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder={t("password.placeholder")}
                        {...field}
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <div className="flex justify-end my-2">
                <span className=" text-sm hover:underline hover:cursor-pointer">
                  {t("forgot-password")}
                </span>
              </div>
            </div>

            <div className="my-2">
              <Button type="submit">{t("login-button")}</Button>
            </div>
          </form>
        </Form>
      </CardContent>
    </Card>
  );
}
