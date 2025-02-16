"use client";

import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { X } from "lucide-react";

interface ModalProps {
  children: React.ReactNode;
  title: string;
}

export default function Modal({ children, title }: ModalProps) {
  const router = useRouter();

  useEffect(() => {
    const clickHandler = () => {
      router.back();
    };

    window.addEventListener("click", clickHandler);

    return () => window.removeEventListener("click", clickHandler);
  });

  return (
    <div
      onClick={(e) => {
        e.stopPropagation();
      }}
      className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 border border-black rounded-lg w-4/5 h-4/5 p-5"
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
