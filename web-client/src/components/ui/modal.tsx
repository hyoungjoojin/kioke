"use client";

import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { X } from "lucide-react";
import { Separator } from "@/components/ui/separator";
import { cn } from "@/lib/utils";

type ModalVariantType = "sm" | "lg";

interface ModalProps {
  children: React.ReactNode;
  controls?: React.ReactNode;
  title: string | React.ReactNode;
  variant?: ModalVariantType;
}

const CloseButton = ({ onClick }: { onClick?: () => void }) => {
  return (
    <button
      type="button"
      className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
      onClick={onClick}
    >
      <X />
    </button>
  );
};

export default function Modal({
  children,
  title,
  controls = null,
  variant = "lg",
}: ModalProps) {
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
        className={cn(
          "absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2",
          "bg-white shadow-lg dark:bg-gray-700 border",
          "w-4/5 h-4/5 p-5",
          "rounded-lg",
        )}
      >
        <div className="flex w-full justify-between items-center h-12 mb-5">
          <h1 className="text-2xl">{title}</h1>
          <div className="flex justify-center items-center">
            {controls}
            <CloseButton
              onClick={() => {
                router.back();
              }}
            />
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
      className={cn(
        "absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2",
        "bg-white shadow-lg dark:bg-gray-700 border",
        "lg:w-96 w-1/2 h-3/5 p-5",
        "rounded-lg",
      )}
    >
      <div className="flex w-full justify-between items-center h-12">
        <h1 className="text-xl">{title}</h1>
        <CloseButton
          onClick={() => {
            router.back();
          }}
        />
      </div>
      <Separator className="my-2" />
      {children}
    </div>
  );
}
