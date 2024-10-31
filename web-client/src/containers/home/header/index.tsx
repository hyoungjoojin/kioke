import Image from "next/image";
import { FaRegUserCircle } from "react-icons/fa";

export default function Header() {
  return (
    <header className="flex justify-between">
      <div>
        <Image
          aria-hidden
          src="/assets/logo/kioke-logo-nocircle.svg"
          alt="kioke"
          width={100}
          height={100}
        />
      </div>

      <div className="p-5">
        <FaRegUserCircle size={32} color="black" title="User Information" />
      </div>
    </header>
  );
}
