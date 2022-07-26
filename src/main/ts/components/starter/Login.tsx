import React, {FC, useEffect, useState} from "react";
import {useLoginMutation} from "../../services/auth";
import {Alert, Button, Col, Form, Row} from "react-bootstrap";
import {StarterTabProps} from "./Starter";
import {PasswordInput} from "./PasswordInput";


export const Login: FC<StarterTabProps> = ({alert}) => {
    const [loginPost, {data, isSuccess, isError}] = useLoginMutation();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        if (isError) alert('danger', 'Incorrect username/password');
    }, [isError])

    const submitForm = (e: React.FormEvent<HTMLFormElement>) =>  {
        e.preventDefault();
        if (e.currentTarget.checkValidity()) loginPost({username, password});
    }

    const onUsernameChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
        alert('primary', '')
    }
    const onPasswordChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
        alert('primary', '')
    }
    return (
        <div>
            <Form noValidate onSubmit={submitForm}>
                <Form.Group className="mb-3">
                    <Form.Label>Username</Form.Label>
                    <Form.Control type="text" placeholder="Enter username" required maxLength={15} onChange={onUsernameChanged} value={username}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Password</Form.Label>
                    <PasswordInput onChanged={onPasswordChanged} isValid={false} isInvalid={false} value={password}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Button type="submit" disabled={username=='' || password==''}>Log in</Button>
                </Form.Group>
            </Form>
        </div>
    )
}