import { Input } from "@/components/ui/input";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Home, Search } from "lucide-react";
import Link from "next/link";

export default function Social() {
  return (
    <>
      <header className="absolute">
        <Link
          className="flex justify-center items-center hover:cursor-pointer my-4 ml-4"
          href="/"
        >
          <Home width={18} className="mr-1" />
          <span className="text-sm">Home</span>
        </Link>
      </header>

      <main className="h-screen w-full flex flex-col justify-center items-center">
        <h1 className="text-3xl my-5">Social</h1>
        <Input
          placeholder="Search users"
          className="mb-5 w-4/5 lg:w-[36rem] rounded-xl"
          head={<Search width={20} />}
        />

        <Tabs defaultValue="friends" className="w-4/5 lg:w-[36rem] h-3/5">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="friends">Friends</TabsTrigger>
            <TabsTrigger value="following">Following</TabsTrigger>
          </TabsList>

          <TabsContent value="friends">Friends</TabsContent>
          <TabsContent value="following">Following</TabsContent>
        </Tabs>
      </main>
    </>
  );
}
