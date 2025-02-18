"use client";

import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { X } from "lucide-react";
import { Separator } from "@/components/ui/separator";

type ModalVariantType = "sm" | "lg";

interface ModalProps {
  children: React.ReactNode;
  title: string;
  variant?: ModalVariantType;
}

export default function Modal({ children, title, variant = "lg" }: ModalProps) {
  const router = useRouter();

  useEffect(() => {
    const clickHandler = () => {
      router.back();
    };

    window.addEventListener("click", clickHandler);

    return () => window.removeEventListener("click", clickHandler);
  });

  if (variant === "lg") {
    return (
      <div
        onClick={(e) => {
          e.stopPropagation();
        }}
        className="bg-white absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 border border-black rounded-lg w-4/5 h-4/5 p-5"
      >
        <div className="flex w-full justify-between items-center h-12 mb-5">
          <h1 className="text-2xl">{title}</h1>
          <div
            onClick={() => {
              router.back();
            }}
          >
            <X />
          </div>
        </div>
        <div>{children}</div>
      </div>
    );
  }

  return (
    <div
      onClick={(e) => {
        e.stopPropagation();
      }}
      className="bg-white absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 border border-black rounded-lg lg:w-96 w-1/2 h-3/5 p-5"
    >
      <div className="flex w-full justify-between items-center h-12">
        <h1 className="text-xl">{title}</h1>
        <div
          onClick={() => {
            router.back();
          }}
        >
          <X />
        </div>
      </div>
      <Separator className="my-2" />
      {children}
    </div>
  );
}
