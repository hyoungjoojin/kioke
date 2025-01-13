"use client";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

const LoginFormSchema = z.object({
  email: z.string().email({ message: "Invalid email address." }),
  password: z.string().nonempty({ message: "Password is empty." }),
});

export default function Login() {
  const loginForm = useForm<z.infer<typeof LoginFormSchema>>({
    resolver: zodResolver(LoginFormSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const formSubmitHandler = (values: z.infer<typeof LoginFormSchema>) => {
    console.log(values);
  };

  return (
    <div className="flex w-full h-dvh items-center justify-center bg-gray-50">
      <Card className="w-1/2">
        <CardHeader>
          <CardTitle>Login to your kioke account.</CardTitle>
          <CardDescription>
            Start your journey towards endless possibilities.
          </CardDescription>
        </CardHeader>

        <CardContent>
          <Form {...loginForm}>
            <form onSubmit={loginForm.handleSubmit(formSubmitHandler)}>
              <div className="my-3">
                <FormField
                  name="email"
                  control={loginForm.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="flex justify-between items-center">
                        <div className="text-black">Email</div>
                        <FormMessage />
                      </FormLabel>
                      <FormControl>
                        <Input placeholder="Enter your email" {...field} />
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
                        <div className="text-black">Password</div>
                        <FormMessage />
                      </FormLabel>
                      <FormControl>
                        <Input
                          type="password"
                          placeholder="Enter your password"
                          {...field}
                        />
                      </FormControl>
                    </FormItem>
                  )}
                />
                <div className="flex justify-end my-2">
                  <span className=" text-sm hover:underline hover:cursor-pointer">
                    Forgot your password?
                  </span>
                </div>
              </div>

              <div className="my-2">
                <Button type="submit">Login</Button>
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
}
